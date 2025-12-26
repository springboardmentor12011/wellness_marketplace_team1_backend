package com.example.wellness_javaproj.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PractitionerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 75]

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // [cite: 76]

    private String specialization; // [cite: 76]
    private boolean verified; // [cite: 76, 133]
    private Double rating; // [cite: 76]

    // Manually add this to ensure the Service filter works
    public boolean isVerified() {
        return verified; // [cite: 76]
    }
}