package com.infosys.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPractitionerId(Long practitionerId);
}
