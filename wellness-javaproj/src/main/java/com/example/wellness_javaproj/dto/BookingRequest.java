package com.example.wellness_javaproj.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long practitionerId; // [cite: 78]
    private Long userId; // [cite: 79]
    private String date; // format: YYYY-MM-DDTHH:mm:ss [cite: 80]
    private String notes; // [cite: 81]
}