package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.QuestionRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.Question;
import com.infosys.entity.User;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository qr;

    @Autowired
    private UserRepository ur;

    public Question askQuestion(Long userId, Question question) {

        User user = ur.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        question.setUser(user);
        question.setCreatedAt(LocalDateTime.now());

        return qr.save(question);
    }

    public List<Question> getAllQuestions() {
        return qr.findAll();
    }

    public Question getQuestionById(Long id) {
        return qr.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }
}
