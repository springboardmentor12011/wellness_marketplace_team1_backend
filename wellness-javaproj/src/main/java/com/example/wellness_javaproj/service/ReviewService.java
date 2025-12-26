package com.example.wellness_javaproj.service;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService { // You can keep Review logic in a specialized service or ForumService
    private ReviewRepository reviewRepo;
    private UserRepository userRepo;
    private PractitionerProfileRepository practitionerProfileRepo;

    public void ReviewService(ReviewRepository reviewRepo, UserRepository userRepo, PractitionerProfileRepository practitionerProfileRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.practitionerProfileRepo = practitionerProfileRepo;
    }

    public ReviewService(ReviewRepository reviewRepo, UserRepository userRepo, PractitionerProfileRepository practitionerProfileRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.practitionerProfileRepo = practitionerProfileRepo;
    }

    public Review addReview(Long userId, Long practitionerId, Double ratingScore, String comment) {
        User user = userRepo.findById(userId).orElseThrow();
        User practitioner = userRepo.findById(practitionerId).orElseThrow();

        Review review = new Review();
        review.setUser(user);
        review.setPractitioner(practitioner);
        review.setRating(ratingScore);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        // Update the average rating on the Practitioner's Profile
        updatePractitionerAverageRating(practitionerId);

        return reviewRepo.save(review);
    }

    private void updatePractitionerAverageRating(Long practitionerId) {
        List<Review> reviews = reviewRepo.findByPractitionerId(practitionerId);
        double average = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

        // Find the profile linked to this user and update it
        // Note: You may need a custom finder in PractitionerProfileRepository: findByUserId(Long id)
    }

    public Question askQuestion(Long userId, String content) {
        return null;
    }
}