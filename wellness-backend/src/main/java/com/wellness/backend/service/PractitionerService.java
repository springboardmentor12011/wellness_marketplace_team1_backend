package com.wellness.backend.service;

import com.wellness.backend.dto.*;
import com.wellness.backend.exception.ResourceNotFoundException;
import com.wellness.backend.model.PractitionerProfile;
import com.wellness.backend.model.User;
import com.wellness.backend.repository.PractitionerProfileRepository;
import com.wellness.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PractitionerService {

    private final PractitionerProfileRepository practitionerRepo;
    private final UserRepository userRepository;

    public PractitionerService(PractitionerProfileRepository practitionerRepo,
                               UserRepository userRepository) {
        this.practitionerRepo = practitionerRepo;
        this.userRepository = userRepository;
    }

    public List<PractitionerResponse> listAllPractitioners() {
        return practitionerRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PractitionerResponse getPractitionerById(Long id) {
        PractitionerProfile p = practitionerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Practitioner not found: " + id));
        return toResponse(p);
    }

    @Transactional
    public PractitionerResponse updatePractitionerProfile(Long practitionerProfileId, PractitionerUpdateRequest req, String userEmail) {
        PractitionerProfile profile = practitionerRepo.findById(practitionerProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Practitioner not found: " + practitionerProfileId));

        // ensure the authenticated user owns this profile
        if (!profile.getUser().getEmail().equals(userEmail)) {
            throw new IllegalStateException("Not authorized to update this profile");
        }

        if (req.getSpecialization() != null) profile.setSpecialization(req.getSpecialization());
        if (req.getBio() != null) profile.getUser().setBio(req.getBio());

        // user entity is owner, so save user will cascade? to be safe save both
        userRepository.save(profile.getUser());
        PractitionerProfile saved = practitionerRepo.save(profile);
        return toResponse(saved);
    }

    @Transactional
    public VerifyResponse verifyPractitioner(Long practitionerProfileId) {
        PractitionerProfile profile = practitionerRepo.findById(practitionerProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Practitioner not found: " + practitionerProfileId));
        profile.setVerified(true);
        practitionerRepo.save(profile);
        return new VerifyResponse(profile.getId(), true);
    }

    private PractitionerResponse toResponse(PractitionerProfile p) {
        User u = p.getUser();
        return PractitionerResponse.builder()
                .id(p.getId())
                .userId(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .bio(u.getBio())
                .specialization(p.getSpecialization())
                .verified(p.isVerified())
                .rating(p.getRating())
                .role(u.getRole())
                .build();
    }
}