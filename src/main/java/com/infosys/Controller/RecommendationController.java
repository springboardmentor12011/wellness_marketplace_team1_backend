package com.infosys.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.RecommendationService;
import com.infosys.entity.Recommendation;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @PostMapping("/generate/{userId}")
    public Recommendation generate(
            @PathVariable Long userId,
            @RequestBody Map<String, String> body) {

        return service.generateRecommendation(userId, body.get("symptom"));
    }

    @GetMapping("/user/{userId}")
    public List<Recommendation> getUserRecommendations(@PathVariable Long userId) {
        return service.getUserRecommendations(userId);
    }
}
