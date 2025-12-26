package com.example.wellness_javaproj.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 98]

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // [cite: 99]

    @Column(columnDefinition = "TEXT")
    private String content; // [cite: 100]
    private LocalDateTime createdAt; // [cite: 101]

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}