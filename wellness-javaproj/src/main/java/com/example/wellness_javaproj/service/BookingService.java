package com.example.wellness_javaproj.service;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final TherapySessionRepository sessionRepo;
    private final PractitionerProfileRepository practitionerRepo;
    private final UserRepository userRepo;

    public BookingService(TherapySessionRepository sessionRepo,
                          PractitionerProfileRepository practitionerRepo,
                          UserRepository userRepo) {
        this.sessionRepo = sessionRepo;
        this.practitionerRepo = practitionerRepo;
        this.userRepo = userRepo;
    }

    /**
     * Requirement: Browse and book therapy sessions [cite: 46]
     * Module A: Practitioner onboarding and verification
     */
    public List<PractitionerProfile> getAllVerifiedPractitioners() {
        return practitionerRepo.findAll().stream()
                .filter(PractitionerProfile::isVerified) //[cite: 76]
                .collect(Collectors.toList());
    }

    /**
     * Requirement: Marketplace for booking therapy sessions [cite: 10]
     * Database: TherapySession (practitionerId, userId, date, status, notes) [cite: 78, 79, 80, 81]
     */
    public TherapySession bookSession(Long patientId, Long practitionerId, LocalDateTime dateTime, String notes) {
        User patient = userRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User practitioner = userRepo.findById(practitionerId)
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        TherapySession session = new TherapySession();
        session.setUser(patient); //[cite: 79]
        session.setPractitioner(practitioner); //[cite: 78]
        session.setDate(dateTime); //[cite: 80]
        session.setStatus("booked"); //[cite: 81]
        session.setNotes(notes); //[cite: 81]

        return sessionRepo.save(session);
    }

    /**
     * Outcome: Session detail page [cite: 51]
     */
    public TherapySession getSessionById(Long id) {
        return sessionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    /**
     * Outcome: User dashboard with session history [cite: 40]
     */
    public List<TherapySession> getSessionsByUserId(Long userId) {
        return sessionRepo.findByUserId(userId);
    }
}