package com.wellness.backend.repository;

import com.wellness.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TherapySessionRepository extends JpaRepository<TherapySession, Long> {

    // ----------------------------------
    // PATIENT (User -> patient)
    // ----------------------------------

    @Query("SELECT ts FROM TherapySession ts WHERE ts.patient = :user")
    List<TherapySession> findByUser(@Param("user") User user);

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.patient = :user
        AND ts.sessionTime > :now
    """)
    List<TherapySession> findByUserAndSessionTimeAfter(
            @Param("user") User user,
            @Param("now") LocalDateTime now
    );

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.patient = :user
        AND ts.sessionTime < :now
    """)
    List<TherapySession> findByUserAndSessionTimeBefore(
            @Param("user") User user,
            @Param("now") LocalDateTime now
    );

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.patient = :user
        AND ts.sessionTime BETWEEN :start AND :end
    """)
    List<TherapySession> findPatientSessionsBetween(
            @Param("user") User user,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // ----------------------------------
    // PRACTITIONER
    // ----------------------------------

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.practitioner.user = :user
    """)
    List<TherapySession> findByPractitionerUser(
            @Param("user") User user
    );

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.practitioner.user = :user
        AND ts.sessionTime > :now
    """)
    List<TherapySession> findByPractitionerAndSessionTimeAfter(
            @Param("user") User user,
            @Param("now") LocalDateTime now
    );

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.practitioner.user = :user
        AND ts.sessionTime BETWEEN :start AND :end
    """)
    List<TherapySession> findPractitionerSessionsBetween(
            @Param("user") User user,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

//Find booked slots
    @Query("""
    	    SELECT ts.sessionTime FROM TherapySession ts
    	    WHERE ts.practitioner.id = :practitionerId
    	    AND DATE(ts.sessionTime) = :date
    	    AND ts.status = 'BOOKED'
    	""")
    	List<LocalDateTime> findBookedSlots(
    	        @Param("practitionerId") Long practitionerId,
    	        @Param("date") LocalDate date
    	);

    // ----------------------------------
    // CONFLICT CHECK
    // ----------------------------------

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.practitioner = :practitioner
        AND ts.sessionTime BETWEEN :start AND :end
        AND ts.status = 'SCHEDULED'
    """)
    List<TherapySession> findConflictingSessions(
            @Param("practitioner") PractitionerProfile practitioner,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // ----------------------------------
    // REMINDERS
    // ----------------------------------

    @Query("""
        SELECT ts FROM TherapySession ts
        WHERE ts.sessionTime BETWEEN :now AND :reminderTime
        AND ts.status = 'SCHEDULED'
    """)
    List<TherapySession> findSessionsForReminders(
            @Param("now") LocalDateTime now,
            @Param("reminderTime") LocalDateTime reminderTime
    );

    // ----------------------------------
    // STATUS
    // ----------------------------------

    List<TherapySession> findByStatus(SessionStatus status);
}
