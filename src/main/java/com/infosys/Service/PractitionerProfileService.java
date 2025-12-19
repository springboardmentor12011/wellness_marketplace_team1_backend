package com.infosys.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.PractitionerProfileRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.PractitionerProfile;
import com.infosys.entity.User;

@Service
public class PractitionerProfileService {

    private final PractitionerProfileRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public PractitionerProfileService(PractitionerProfileRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Create practitioner profile
    public Object createProfile(PractitionerProfile profile) {
        Long userId = profile.getUser().getId();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "User not found";
        }

        if (repository.findByUserId(userId).isPresent()) {
            return "Profile already exists for this user";
        }

        if (!"Practitioner".equalsIgnoreCase(user.getRole())) {
            return "Cannot create practitioner profile. User role is '" + user.getRole() + "'";
        }

        // ✅ Set the user and mark profile as verified
        profile.setUser(user);
        profile.setVerified(true);

        return repository.save(profile); // returns the saved profile
    }



    // Get profile by id
    public PractitionerProfile getProfileById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Practitioner profile not found"));
    }

    // Get all practitioner profiles
    public List<PractitionerProfile> getAllProfiles() {
        return repository.findAll();
    }
 // Service: PractitionerProfileService.java
    public PractitionerProfile updateRating(Long practitionerId, Double rating) {
        PractitionerProfile profile = repository.findById(practitionerId)
            .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        profile.setRating(rating);
        return repository.save(profile);
    }




}
