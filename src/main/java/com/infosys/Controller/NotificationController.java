package com.infosys.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infosys.Service.NotificationService;
import com.infosys.entity.Notification;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/send/{userId}")
    public Notification sendNotification(
            @PathVariable Long userId,
            @RequestBody Map<String, String> body) {

        return service.sendNotification(
                userId,
                body.get("type"),
                body.get("message")
        );
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return service.getUserNotifications(userId);
    }
}
