package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.AnswerRepository;
import com.infosys.Repo.PractitionerProfileRepository;
import com.infosys.Repo.QuestionRepository;
import com.infosys.entity.Answer;
import com.infosys.entity.PractitionerProfile;
import com.infosys.entity.Question;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository ar;

    @Autowired
    private QuestionRepository qr;

    @Autowired
    private PractitionerProfileRepository pr;

    public Answer addAnswer(Long questionId, Long practitionerId, Answer answer) {

        Question question = qr.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        PractitionerProfile practitioner = pr.findById(practitionerId)
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        answer.setQuestion(question);
        answer.setPractitioner(practitioner);
        answer.setCreatedAt(LocalDateTime.now());

        return ar.save(answer);
    }

    public List<Answer> getAnswers(Long questionId) {
        return ar.findByQuestionId(questionId);
    }
}

