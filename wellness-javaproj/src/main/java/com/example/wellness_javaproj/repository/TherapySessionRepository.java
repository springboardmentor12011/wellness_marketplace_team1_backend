package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.TherapySession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TherapySessionRepository extends JpaRepository<TherapySession, Long> {
    List<TherapySession> findByUserId(Long userId);
    List<TherapySession> findByPractitionerId(Long practitionerId);
}