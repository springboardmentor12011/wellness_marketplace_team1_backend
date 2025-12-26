package com.example.wellness_javaproj.Controller;

import com.example.wellness_javaproj.model.User;
import com.example.wellness_javaproj.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user); // Handles registration [cite: 33]
    }

    // Add /login endpoint to return JWT Access + Refresh tokens [cite: 24, 33]
}