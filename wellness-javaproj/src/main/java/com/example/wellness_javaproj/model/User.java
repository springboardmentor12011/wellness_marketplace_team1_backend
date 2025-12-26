package com.example.wellness_javaproj.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 70]
    private String name; // [cite: 70]
    @Column(unique = true)
    private String email; // [cite: 71]
    private String password; // [cite: 72]
    private String role; // patient or practitioner [cite: 72]
    private String bio; // [cite: 72]
}