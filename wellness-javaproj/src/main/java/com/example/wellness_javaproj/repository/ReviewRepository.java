package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPractitionerId(Long practitionerId); // To show reviews on practitioner profile [cite: 7]
}