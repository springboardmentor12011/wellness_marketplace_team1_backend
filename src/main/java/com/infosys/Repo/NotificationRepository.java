package com.infosys.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.infosys.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);
}
