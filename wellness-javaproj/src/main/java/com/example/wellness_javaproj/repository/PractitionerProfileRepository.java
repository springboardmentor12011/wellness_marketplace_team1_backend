package com.example.wellness_javaproj.repository;

import com.example.wellness_javaproj.model.PractitionerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PractitionerProfileRepository extends JpaRepository<PractitionerProfile, Long> {
}