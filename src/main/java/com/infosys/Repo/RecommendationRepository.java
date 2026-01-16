package com.infosys.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    List<Recommendation> findByUserId(Long userId);
}
