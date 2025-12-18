package com.wellness.backend.dto;

import com.wellness.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PractitionerResponse {
    private Long id;               // practitioner profile id
    private Long userId;
    private String name;
    private String email;
    private String bio;
    private String specialization;
    private boolean verified;
    private Double rating;
    private Role role;
}
