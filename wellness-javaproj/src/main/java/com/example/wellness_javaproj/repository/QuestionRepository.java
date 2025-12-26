package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles community questions for the Q&A forum module (Module D)[cite: 19].
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Used to retrieve community questions for practitioner interaction [cite: 4, 12]
}