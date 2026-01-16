package com.infosys.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
}
