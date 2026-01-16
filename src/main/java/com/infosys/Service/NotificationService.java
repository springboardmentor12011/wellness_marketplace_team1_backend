package com.infosys.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.Repo.NotificationRepository;
import com.infosys.Repo.UserRepository;
import com.infosys.entity.Notification;
import com.infosys.entity.User;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository nr;

    @Autowired
    private UserRepository ur;

    public Notification sendNotification(Long userId, String type, String message) {

        User user = ur.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification n = new Notification();
        n.setUser(user);
        n.setType(type);
        n.setMessage(message);
        n.setStatus("UNREAD");
        n.setCreatedAt(LocalDateTime.now());

        return nr.save(n);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return nr.findByUserId(userId);
    }
}
