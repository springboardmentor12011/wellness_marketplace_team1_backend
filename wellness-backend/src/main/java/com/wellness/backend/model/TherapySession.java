package com.wellness.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PATIENT
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"password"})
    private User patient;

    // PRACTITIONER
    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    @JsonIgnoreProperties({"user"})
    private PractitionerProfile practitioner;

    private LocalDateTime sessionTime;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Enumerated(EnumType.STRING)
    private SessionMode mode; // ONLINE / OFFLINE

    private String googleEventId; // 🔥 REQUIRED for calendar sync
    
    @Enumerated(EnumType.STRING)
    private TherapyType therapyType;

}
