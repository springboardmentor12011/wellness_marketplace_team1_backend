package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.TherapySessionRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.Repo.PractitionerProfileRepository;

import com.infosys.entity.TherapySession;
import com.infosys.entity.User;
import com.infosys.entity.PractitionerProfile;

@Service
public class TherapySessionService {

    @Autowired
    private TherapySessionRepository sr;

    @Autowired
    private UserRepository ur;

    @Autowired
    private PractitionerProfileRepository pr;

    // ✅ BOOK SESSION
    public Object bookSession(Long userId, Long practitionerId, TherapySession session) {

        // fetch patient
        Optional<User> patientOpt = ur.findById(userId);
        if (!patientOpt.isPresent()) {
            return Map.of("message", "Patient not found");
        }

        User patient = patientOpt.get();

        // patient only allowed
        if (!patient.getRole().equalsIgnoreCase("Patient")) {
            return Map.of("message", "Session can be booked only for Patients");
        }

        // fetch practitioner
        Optional<PractitionerProfile> pracOpt = pr.findById(practitionerId);
        if (!pracOpt.isPresent()) {
            return Map.of("message", "Practitioner not found");
        }

        PractitionerProfile practitioner = pracOpt.get();

        // ------------- VALIDATIONS ---------------------

        // 1️⃣ prevent booking in past
        if (session.getDate().isBefore(LocalDateTime.now())) {
            return Map.of("message", "Cannot book session in past");
        }

        // 2️⃣ prevent patient duplicate booking same time
        if (sr.existsByUserIdAndDate(userId, session.getDate())) {
            return Map.of("message", "Patient already has session at this time");
        }

        // 3️⃣ prevent practitioner double booking same slot
        if (sr.existsByPractitionerIdAndDate(practitionerId, session.getDate())) {
            return Map.of("message", "Practitioner already booked for this slot");
        }

        // ------------------------------------------------


        // attach relations
        session.setUser(patient);
        session.setPractitioner(practitioner);

        // default session status
        if (session.getStatus() == null) {
            session.setStatus("BOOKED");
        }

        TherapySession savedSession = sr.save(session);

        return savedSession;
    }


    // ✅ CANCEL SESSION
    public Object cancelSession(Long sessionId) {
        Optional<TherapySession> sessionOpt = sr.findById(sessionId);
        if (!sessionOpt.isPresent()) {
            return Map.of("message", "Session not found");
        }

        TherapySession session = sessionOpt.get();

        if ("CANCELLED".equalsIgnoreCase(session.getStatus())) {
            return Map.of("message", "Session already cancelled");
        }

        session.setStatus("CANCELLED");
        sr.save(session);

        return Map.of("message", "Session cancelled successfully");
    }


    // ❗ LIST ONLY BOOKED
    public Object getBookedSessions() {
        return sr.findByStatus("BOOKED");
    }

    // ❗ LIST CANCELLED
    public Object getCancelledSessions() {
        return sr.findByStatus("CANCELLED");
    }
    
 // Get session details by ID
    public Object getSessionById(Long sessionId) {
        Optional<TherapySession> sessionOpt = sr.findById(sessionId);
        if (!sessionOpt.isPresent()) {
            return Map.of("message", "Session not found");
        }
        return sessionOpt.get();
    }


public List<TherapySession> getSessionsByPatient(Long userId) {
    return sr.findByUserId(userId);
}

public List<TherapySession> getSessionsByPractitioner(Long practitionerId) {
    return sr.findByPractitionerId(practitionerId);
}
 


public List<Map<String, Object>> getSessionsForCalendar(Long practitionerId) {
    List<TherapySession> sessions = sr.findByPractitionerId(practitionerId);

    return sessions.stream().map(s -> {
        Map<String, Object> map = new HashMap<>();
        map.put("id", s.getId());
        map.put("title", s.getUser().getName() + " - " + s.getStatus());
        map.put("start", s.getDate().toString());
        map.put("status", s.getStatus());
        return map;
    }).collect(Collectors.toList());


  }
}
