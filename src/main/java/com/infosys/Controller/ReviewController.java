package com.infosys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.ReviewService;
import com.infosys.entity.Review;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping("/add/{userId}/{practitionerId}")
    public Review addReview(
            @PathVariable Long userId,
            @PathVariable Long practitionerId,
            @RequestBody Review review) {

        return service.addReview(userId, practitionerId, review);
    }

    @GetMapping("/practitioner/{id}")
    public List<Review> getReviews(@PathVariable Long id) {
        return service.getReviews(id);
    }
}
