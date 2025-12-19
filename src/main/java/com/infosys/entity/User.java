package com.infosys.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String bio;

    // ✅ CORRECT ONE-TO-ONE MAPPING
    // Hide practitionerProfile from JSON if null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PractitionerProfile practitionerProfile;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<TherapySession> sessions;

}
