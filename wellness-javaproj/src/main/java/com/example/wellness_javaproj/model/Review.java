package com.example.wellness_javaproj.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 91]

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // [cite: 92]

    @ManyToOne
    @JoinColumn(name = "practitioner_id")
    private User practitioner; // [cite: 93]

    private Double rating; // [cite: 94]

    @Column(columnDefinition = "TEXT")
    private String comment; // [cite: 95]

    private LocalDateTime createdAt; // [cite: 96]

    // Manual Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public User getPractitioner() { return practitioner; }
    public void setPractitioner(User practitioner) { this.practitioner = practitioner; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}