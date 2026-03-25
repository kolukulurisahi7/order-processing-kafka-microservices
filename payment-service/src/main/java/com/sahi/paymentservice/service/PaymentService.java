package com.sahi.paymentservice.service;

import com.sahi.paymentservice.dto.PaymentResponse;
import com.sahi.paymentservice.model.Payment;
import com.sahi.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Payment> kafkaTemplate;

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void processOrder(Payment order) {  // Assuming Order is sent, but to simplify, use Payment or adjust
        // In real, Order is sent, but for simplicity, assume order has id, price
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice() * order.getQuantity());
        payment.setStatus("SUCCESS");

        payment = paymentRepository.save(payment);

        kafkaTemplate.send("payment-topic", payment);

        log.info("Payment processed for order: {}", order.getId());
    }
}