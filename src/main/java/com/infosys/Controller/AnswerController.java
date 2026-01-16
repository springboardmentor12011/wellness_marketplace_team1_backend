package com.infosys.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.AnswerService;
import com.infosys.entity.Answer;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService service;

    @PostMapping("/add/{questionId}/{practitionerId}")
    public Answer addAnswer(
            @PathVariable Long questionId,
            @PathVariable Long practitionerId,
            @RequestBody Answer answer) {

        return service.addAnswer(questionId, practitionerId, answer);
    }

    @GetMapping("/question/{questionId}")
    public List<Answer> getAnswers(@PathVariable Long questionId) {
        return service.getAnswers(questionId);
    }
}
