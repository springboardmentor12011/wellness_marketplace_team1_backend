package com.example.wellness_javaproj.service;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PractitionerProfileRepository practitionerRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PractitionerProfileRepository practitionerRepo, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.practitionerRepo = practitionerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypted [cite: 72]
        User savedUser = userRepository.save(user);

        if ("practitioner".equalsIgnoreCase(user.getRole())) {
            PractitionerProfile profile = new PractitionerProfile();
            profile.setUser(savedUser);
            practitionerRepo.save(profile); // Onboarding [cite: 17]
        }
        return "User registered successfully";
    }
}