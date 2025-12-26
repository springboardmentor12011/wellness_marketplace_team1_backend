package com.example.wellness_javaproj.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data // This generates all getters and setters automatically
public class TherapySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 77, 78]

    @ManyToOne
    @JoinColumn(name = "practitioner_id")
    private User practitioner; // [cite: 78, 145]

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // [cite: 79]

    private LocalDateTime date; // [cite: 80]
    private String status; // [cite: 81, 89]
    private String notes; // [cite: 81]

    public void setUser(User patient) {
    }

    public void setPractitioner(User practitioner) {
    }

    public void setDate(LocalDateTime dateTime) {
    }

    public void setStatus(String booked) {
    }

    public void setNotes(String notes) {
    }
}