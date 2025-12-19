package com.infosys.Repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.infosys.entity.PractitionerProfile;

@Repository
public interface PractitionerProfileRepository
        extends JpaRepository<PractitionerProfile, Long> {

    Optional<PractitionerProfile> findByUserId(Long userId);
}
