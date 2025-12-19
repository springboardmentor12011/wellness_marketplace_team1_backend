package com.wellness.backend.service;

import com.wellness.backend.integration.GoogleCalendarService.java.GoogleCalendarService;
import com.wellness.backend.model.*;
import com.wellness.backend.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TherapySessionService {

    private final TherapySessionRepository therapySessionRepository;
    private final UserRepository userRepository;
    private final PractitionerProfileRepository practitionerProfileRepository;
    private final GoogleCalendarService googleCalendarService;

    public TherapySessionService(
            TherapySessionRepository therapySessionRepository,
            UserRepository userRepository,
            PractitionerProfileRepository practitionerProfileRepository,
            GoogleCalendarService googleCalendarService
    ) {
        this.therapySessionRepository = therapySessionRepository;
        this.userRepository = userRepository;
        this.practitionerProfileRepository = practitionerProfileRepository;
        this.googleCalendarService = googleCalendarService;
    }

    // -----------------------------------------
    // BOOK A THERAPY SESSION
    // -----------------------------------------
    public TherapySession bookSession(
            Long patientId,
            Long practitionerId,
            LocalDateTime time,
            SessionMode mode
    ) {
        validateSessionTime(time);

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PractitionerProfile practitioner = practitionerProfileRepository.findById(practitionerId)
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        validateSlotAvailability(practitioner, time);

        TherapySession session = TherapySession.builder()
                .patient(patient)
                .practitioner(practitioner)
                .sessionTime(time)
                .mode(mode)
                .status(SessionStatus.BOOKED)
                .build();

        session = therapySessionRepository.save(session);
        googleCalendarService.createEvent(session);

        return session;
    }

    // -----------------------------------------
    // UPCOMING SESSIONS (PATIENT)
    // -----------------------------------------
    public List<TherapySession> getUpcomingSessions(Long patientId) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return therapySessionRepository
                .findByUserAndSessionTimeAfter(patient, LocalDateTime.now());
    }

    // -----------------------------------------
    // SESSION HISTORY (PATIENT)
    // -----------------------------------------
    public List<TherapySession> getSessionHistory(Long patientId) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return therapySessionRepository
                .findByUserAndSessionTimeBefore(patient, LocalDateTime.now());
    }

    // -----------------------------------------
    // CANCEL SESSION
    // -----------------------------------------
    public void cancelSession(Long sessionId, Long patientId) {

        TherapySession session = therapySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getPatient().getId().equals(patientId)) {
            throw new AccessDeniedException("Not allowed to cancel this session");
        }

        session.setStatus(SessionStatus.CANCELLED);
        therapySessionRepository.save(session);

        googleCalendarService.deleteEvent(session);
    }

    // -----------------------------------------
    // RESCHEDULE SESSION
    // -----------------------------------------
    public TherapySession rescheduleSession(
            Long sessionId,
            Long patientId,
            LocalDateTime newTime
    ) {
        validateSessionTime(newTime);

        TherapySession session = therapySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getPatient().getId().equals(patientId)) {
            throw new AccessDeniedException("Not allowed");
        }

        if (session.getStatus() != SessionStatus.BOOKED) {
            throw new IllegalStateException("Only booked sessions can be rescheduled");
        }

        validateSlotAvailability(session.getPractitioner(), newTime);

        session.setSessionTime(newTime);
        session = therapySessionRepository.save(session);

        googleCalendarService.updateEvent(session);
        return session;
    }

    // -----------------------------------------
    // CALENDAR VIEW
    // -----------------------------------------
    public List<TherapySession> getCalendarSessions(
            User user,
            LocalDateTime start,
            LocalDateTime end
    ) {
        if (user.getRole() == Role.PATIENT) {
            return therapySessionRepository
                    .findByUserAndSessionTimeAfter(user, start);
        }

        if (user.getRole() == Role.PRACTITIONER) {
            return therapySessionRepository
                    .findPractitionerSessionsBetween(user, start, end);
        }

        throw new AccessDeniedException("Invalid role");
    }

    // -----------------------------------------
    // AVAILABLE SLOTS (NEW – INTEGRATED)
    // -----------------------------------------
    public List<LocalDateTime> getAvailableSlots(
            Long practitionerId,
            LocalDate date
    ) {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return List.of();
        }

        List<LocalDateTime> booked =
                therapySessionRepository.findBookedSlots(practitionerId, date);

        List<LocalDateTime> available = new ArrayList<>();

        LocalDateTime start = date.atTime(8, 0);
        LocalDateTime end = date.atTime(22, 0);

        while (start.isBefore(end)) {
            if (!booked.contains(start)) {
                available.add(start);
            }
            start = start.plusMinutes(30);
        }

        return available;
    }

    // -----------------------------------------
    // VALIDATIONS
    // -----------------------------------------
    private void validateSessionTime(LocalDateTime time) {

        DayOfWeek day = time.getDayOfWeek();
        LocalTime sessionTime = time.toLocalTime();

        if (day == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("Sessions not available on Sundays");
        }

        if (sessionTime.isBefore(LocalTime.of(8, 0))
                || sessionTime.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalStateException(
                    "Sessions allowed only between 8:00 AM and 10:00 PM"
            );
        }
    }
    public List<TherapySession> getUpcomingSessionsForPatient(User patient) {
        return therapySessionRepository
                .findByUserAndSessionTimeAfter(
                        patient,
                        LocalDateTime.now()
                );
    }
    public List<TherapySession> getUpcomingSessionsForPractitioner(User practitioner) {
        return therapySessionRepository
                .findByPractitionerAndSessionTimeAfter(
                        practitioner,
                        LocalDateTime.now()
                );
    }
    public TherapySession getSessionDetail(Long sessionId, Long userId) {

        TherapySession session = therapySessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        boolean isPatient =
                session.getPatient().getId().equals(userId);

        boolean isPractitioner =
                session.getPractitioner().getUser().getId().equals(userId);

        if (!isPatient && !isPractitioner) {
            throw new AccessDeniedException("Not authorized");
        }

        return session;
    }

    private void validateSlotAvailability(
            PractitionerProfile practitioner,
            LocalDateTime newSessionTime
    ) {
        LocalDateTime start = newSessionTime.minusMinutes(30);
        LocalDateTime end = newSessionTime.plusMinutes(30);

        List<TherapySession> conflicts =
                therapySessionRepository.findConflictingSessions(
                        practitioner,
                        start,
                        end
                );

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Time slot already booked");
        }
    }
}
