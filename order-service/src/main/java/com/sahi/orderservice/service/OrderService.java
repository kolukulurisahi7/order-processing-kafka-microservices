package com.sahi.orderservice.service;

import com.sahi.orderservice.dto.OrderRequest;
import com.sahi.orderservice.dto.OrderResponse;
import com.sahi.orderservice.model.Order;
import com.sahi.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setProduct(orderRequest.getProduct());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setStatus("PENDING");

        order = orderRepository.save(order);

        kafkaTemplate.send("order-topic", order);

        log.info("Order created and sent to Kafka: {}", order.getId());

        return new OrderResponse(order.getId(), order.getProduct(), order.getQuantity(), order.getPrice(), order.getStatus());
    }
}