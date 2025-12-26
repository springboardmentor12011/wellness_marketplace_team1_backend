package com.example.wellness_javaproj.Controller;

import com.example.wellness_javaproj.model.Question;
import com.example.wellness_javaproj.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {
    private final ReviewService forumService;

    public ForumController(ReviewService forumService) {
        this.forumService = forumService;
    }

    // Endpoint for users to ask questions [cite: 54]
    @PostMapping("/ask")
    public Question postQuestion(@RequestParam Long userId, @RequestParam String content) {
        return forumService.askQuestion(userId, content);
    }

    // Endpoint to browse the forum
    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return forumService.getAllQuestions();
    }
}