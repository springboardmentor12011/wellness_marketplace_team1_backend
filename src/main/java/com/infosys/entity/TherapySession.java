package com.infosys.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
public class TherapySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // _id: Unique identifier of therapy session

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private PractitionerProfile practitioner;

    
   
    private LocalDateTime date;  // _date: Scheduled session date and time

    private String status;  // _status: booked / completed

    private String notes;  // _notes: Session notes or feedback
}
