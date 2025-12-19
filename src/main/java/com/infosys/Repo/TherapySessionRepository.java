package com.infosys.Repo;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.entity.TherapySession;

public interface TherapySessionRepository extends JpaRepository<TherapySession, Long> {

    List<TherapySession> findByStatus(String status);

    boolean existsByUserIdAndDate(Long userId, LocalDateTime date);

    boolean existsByPractitionerIdAndDate(Long practitionerId, LocalDateTime date);


List<TherapySession> findByUserId(Long userId);

List<TherapySession> findByPractitionerId(Long practitionerId);



}
