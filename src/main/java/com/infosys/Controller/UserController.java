package com.infosys.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infosys.Repo.UserRepository;
import com.infosys.entity.User;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        // Validation #1 : null / blank fields
        if (user.getName() == null || user.getName().isBlank() ||
            user.getEmail() == null || user.getEmail().isBlank() ||
            user.getPassword() == null || user.getPassword().isBlank() ||
            user.getRole() == null || user.getRole().isBlank()) {

            return ResponseEntity.badRequest().body("All fields are required");
        }

        // Validation #2 : Email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // If everything correct → save
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }


    // Login user (basic check)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {

        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // Validation #1 : null / blank check
        if (email == null || email.isBlank() ||
            password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Email and Password are required");
        }

        // Validation #2: Check if user exists
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok(user);
    }


   
}

