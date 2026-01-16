package com.infosys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.QuestionService;
import com.infosys.entity.Question;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @PostMapping("/ask/{userId}")
    public Question askQuestion(
            @PathVariable Long userId,
            @RequestBody Question question) {

        return service.askQuestion(userId, question);
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return service.getQuestionById(id);
    }
}
