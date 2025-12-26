package com.example.wellness_javaproj.Controller;

import com.example.wellness_javaproj.model.TherapySession;
import com.example.wellness_javaproj.model.PractitionerProfile;
import com.example.wellness_javaproj.service.BookingService;
import com.example.wellness_javaproj.dto.BookingRequest;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Browse practitioners [cite: 46]
    @GetMapping("/practitioners")
    public List<PractitionerProfile> getPractitioners() {
        return bookingService.getAllVerifiedPractitioners();
    }

    // Book a therapy session [cite: 10, 17]
    @PostMapping("/book")
    public TherapySession createBooking(@RequestBody BookingRequest request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getDate());
        return bookingService.bookSession(
                request.getUserId(),
                request.getPractitionerId(),
                dateTime,
                request.getNotes()
        );
    }

    // Session detail page [cite: 51]
    @GetMapping("/{id}")
    public TherapySession getSessionDetails(@PathVariable Long id) {
        return bookingService.getSessionById(id);
    }

    // User dashboard: session history
    @GetMapping("/user/{userId}")
    public List<TherapySession> getUserHistory(@PathVariable Long userId) {
        return bookingService.getSessionsByUserId(userId);
    }
}