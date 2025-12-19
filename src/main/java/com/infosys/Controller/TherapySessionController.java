package com.infosys.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.TherapySessionService;
import com.infosys.entity.TherapySession;

@RestController
@RequestMapping("/api/session")
public class TherapySessionController {

    @Autowired
    private TherapySessionService service;
    
    @PostMapping("/book/{userId}/{practitionerId}")
    public Object bookSession(
            @PathVariable Long userId,
            @PathVariable Long practitionerId,
            @RequestBody TherapySession session) {

        return service.bookSession(userId, practitionerId, session);
    }


    // Cancel session
    @PutMapping("/cancel/{sessionId}")
    public Object cancelSession(@PathVariable Long sessionId) {
        return service.cancelSession(sessionId);
    }
    
 // get all booked sessions
    @GetMapping("/booked")
    public Object getBookedSessions() {
        return service.getBookedSessions();
    }

    // get all cancelled sessions
    @GetMapping("/cancelled")
    public Object getCancelledSessions() {
        return service.getCancelledSessions();
    }

 // Get session details
    @GetMapping("/{sessionId}")
    public Object getSessionDetails(@PathVariable Long sessionId) {
        return service.getSessionById(sessionId);
    }

    
 // Get all sessions for a patient
    @GetMapping("/patient/{userId}")
    public List<TherapySession> getPatientSessions(@PathVariable Long userId) {
        return service.getSessionsByPatient(userId);
    }

    // Get all sessions for a practitioner
    @GetMapping("/practitioner/{practitionerId}")
    public List<TherapySession> getPractitionerSessions(@PathVariable Long practitionerId) {
        return service.getSessionsByPractitioner(practitionerId);
    }

 // Get sessions for calendar view for a practitioner
    @GetMapping("/calendar/{practitionerId}")
    public List<Map<String, Object>> getCalendarSessions(@PathVariable Long practitionerId) {
        return service.getSessionsForCalendar(practitionerId);
    }

}
