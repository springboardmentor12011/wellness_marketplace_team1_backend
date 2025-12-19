package com.infosys.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.UserRepository;
import com.infosys.entity.User;

@Service
public class userService {

    @Autowired
    private final UserRepository ur;

    public userService(UserRepository ur) {
        super();
        this.ur = ur;
    }

    public User register(User user) {
        if (ur.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return ur.save(user);
    }

    // ✅ Role-specific registration
    public User registerPatient(User user) {
        user.setRole("Patient");
        return register(user);
    }

    public User registerPractitioner(User user) {
        user.setRole("Practitioner");
        return register(user);
    }

    public boolean login(String email, String password) {
        User dbUser = ur.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        return dbUser.getPassword().equals(password);
    }

    public User getUserById(Long id) {
        return ur.findById(id)
                 .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User userDetails) {
        User user = ur.findById(id)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setBio(userDetails.getBio());
        // Optional: email update check for duplicates
        return ur.save(user);
    }

    public void deleteUser(Long id) {
        ur.deleteById(id);
    }

    public List<User> getAllUsers() {
        return ur.findAll();
    }
}
