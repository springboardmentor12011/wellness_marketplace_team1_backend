package com.wellness.backend.service;

import com.wellness.backend.dto.UserProfileResponse;
import com.wellness.backend.exception.ResourceNotFoundException;
import com.wellness.backend.model.Role;
import com.wellness.backend.model.User;
import com.wellness.backend.repository.PractitionerProfileRepository;
import com.wellness.backend.repository.UserRepository;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PractitionerProfileRepository practitionerProfileRepository;

    public UserService(UserRepository userRepository,
                       PractitionerProfileRepository practitionerProfileRepository) {
        this.userRepository = userRepository;
        this.practitionerProfileRepository = practitionerProfileRepository;
    }

    // -------------------------------------------------
    // GET USER PROFILE BY EMAIL
    // -------------------------------------------------
    public UserProfileResponse getUserProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + email));

        return mapToProfileResponse(user);
    }

    // -------------------------------------------------
    // GET USER PROFILE BY ID
    // -------------------------------------------------
    public UserProfileResponse getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + id));

        return mapToProfileResponse(user);
    }

    // -------------------------------------------------
    // ✅ ADMIN: GET ALL USERS
    // -------------------------------------------------
    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToProfileResponse)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------
    // DELETE OWN PROFILE (PATIENT / PRACTITIONER)
    // -------------------------------------------------
    public void deleteMyProfile(User currentUser) {

        if (currentUser.getRole() == Role.ADMIN) {
            throw new AccessDeniedException("Admin cannot delete own account");
        }

        if (currentUser.getRole() == Role.PRACTITIONER) {
            practitionerProfileRepository
                    .findByUser(currentUser)
                    .ifPresent(practitionerProfileRepository::delete);
        }

        userRepository.delete(currentUser);
    }

    // -------------------------------------------------
    // ADMIN: DELETE ANY USER
    // -------------------------------------------------
    public void adminDeleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        if (user.getRole() == Role.ADMIN) {
            throw new AccessDeniedException("Admin cannot delete another admin");
        }

        if (user.getRole() == Role.PRACTITIONER) {
            practitionerProfileRepository
                    .findByUser(user)
                    .ifPresent(practitionerProfileRepository::delete);
        }

        userRepository.delete(user);
    }

    // -------------------------------------------------
    // HELPER: ENTITY → DTO
    // -------------------------------------------------
    private UserProfileResponse mapToProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getBio()
        );
    }
}
