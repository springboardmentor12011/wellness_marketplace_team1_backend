package com.example.wellness_javaproj.service;

import com.example.wellness_javaproj.model.*;
import com.example.wellness_javaproj.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {
    private final QuestionRepository questionRepo;
    private final UserRepository userRepo;

    public ForumService(QuestionRepository questionRepo, UserRepository userRepo) {
        this.questionRepo = questionRepo;
        this.userRepo = userRepo;
    }

    // Requirement: Community Q&A forum module
    public Question askQuestion(Long userId, String content) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Question question = new Question();
        question.setUser(user); // [cite: 99]
        question.setContent(content); // [cite: 100]
        question.setCreatedAt(LocalDateTime.now()); // [cite: 101]

        return questionRepo.save(question);
    }

    // Requirement: Browse community questions [cite: 54]
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }
}