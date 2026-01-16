package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.RecommendationRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.Recommendation;
import com.infosys.entity.User;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository rr;

    @Autowired
    private UserRepository ur;

    // Simple AI rule engine
    public Recommendation generateRecommendation(Long userId, String symptom) {

        User user = ur.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String therapy;

        if (symptom.toLowerCase().contains("back")) {
            therapy = "Physiotherapy";
        } else if (symptom.toLowerCase().contains("stress")) {
            therapy = "Yoga & Meditation";
        } else if (symptom.toLowerCase().contains("pain")) {
            therapy = "Acupuncture";
        } else {
            therapy = "General Wellness Consultation";
        }

        Recommendation rec = new Recommendation();
        rec.setUser(user);
        rec.setSymptom(symptom);
        rec.setSuggestedTherapy(therapy);
        rec.setSourceAPI("Internal AI Engine");
        rec.setTimestamp(LocalDateTime.now());

        return rr.save(rec);
    }

    public List<Recommendation> getUserRecommendations(Long userId) {
        return rr.findByUserId(userId);
    }
}
