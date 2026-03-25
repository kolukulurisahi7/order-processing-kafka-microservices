package com.sahi.notificationservice.service;

import com.sahi.notificationservice.model.Notification;
import com.sahi.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "payment-topic", groupId = "notification-group")
    public void processPayment(Notification payment) {  // Assuming Payment is sent
        Notification notification = new Notification();
        notification.setOrderId(payment.getOrderId());
        notification.setMessage("Payment successful for order " + payment.getOrderId());
        notification.setStatus("SENT");

        notificationRepository.save(notification);

        log.info("Notification sent for payment: {}", payment.getId());
    }
}