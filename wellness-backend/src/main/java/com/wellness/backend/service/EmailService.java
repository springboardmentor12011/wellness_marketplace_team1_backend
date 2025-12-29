package com.wellness.backend.service;

import org.springframework.stereotype.Service;

import com.wellness.backend.model.Order;

@Service
public class EmailService {
    public void sendOrderStatusEmail(String to, Order order) {
        System.out.println("Email sent to " + to);
    }
}
