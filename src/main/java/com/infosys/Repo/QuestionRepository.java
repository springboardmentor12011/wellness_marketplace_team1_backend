package com.infosys.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByUserId(Long userId);
}

