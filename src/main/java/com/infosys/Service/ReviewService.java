package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.ReviewRepository;
import com.infosys.Repo.PractitionerProfileRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.Review;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository rr;

    @Autowired
    private UserRepository ur;

    @Autowired
    private PractitionerProfileRepository pr;

    public Review addReview(Long userId, Long practitionerId, Review review) {

        review.setUser(ur.findById(userId).orElseThrow());
        review.setPractitioner(pr.findById(practitionerId).orElseThrow());
        review.setCreatedAt(LocalDateTime.now());

        return rr.save(review);
    }

    public List<Review> getReviews(Long practitionerId) {
        return rr.findByPractitionerId(practitionerId);
    }
}
