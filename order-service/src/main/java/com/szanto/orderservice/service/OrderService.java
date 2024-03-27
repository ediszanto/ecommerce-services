package com.szanto.orderservice.service;

import com.szanto.orderservice.entity.OrderStatus;
import com.szanto.orderservice.entity.PaymentStatus;
import com.szanto.orderservice.entity.PaymentType;
import com.szanto.orderservice.entity.Order;
import com.szanto.orderservice.entity.OrderItem;

import com.szanto.orderservice.exceptions.OrderException;
import com.szanto.orderservice.exceptions.OrderItemNotFoundException;
import com.szanto.orderservice.exceptions.OrderNotFoundException;
import com.szanto.orderservice.kafka.KafkaUtil;
import com.szanto.orderservice.mapper.OrderMapper;
import com.szanto.orderservice.model.OrderRequest;
import com.szanto.orderservice.model.OrderResponse;
import com.szanto.orderservice.repository.OrderItemRespository;
import com.szanto.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final KafkaUtil kafkaUtil;
    private final OrderRepository orderRepository;
    private final OrderItemRespository orderItemRespository;

    private final OrderMapper orderMapper;

    public boolean placeOrder(String userId, OrderRequest orderRequest) {
        if (orderRequest.getOrderItems().isEmpty()) {
            throw new OrderException("Each order should have at least one item");
        }
        Order order = prepareOrder(userId, orderRequest);
        log.info("Sending Order: {}", order);
        return kafkaUtil.sendToKafka(userId, order);
    }

    @Transactional
    public List<OrderResponse> getOrdersOfUser(String userId) {
        return orderRepository.findAllByUserId(userId)
                .map(orderList -> orderList.stream()
                        .map(orderMapper::toResponse).toList())
                .orElse(Collections.emptyList());
    }

    @Transactional
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %d doesn't exist", orderId)));
        order.setOrderItems(orderItemRespository.findByOrderId(orderId).orElseThrow(OrderItemNotFoundException::new));
        return orderMapper.toResponse(order);
    }

    private Double calculateTotalCostOfOrder(List<OrderItem> orderItems) {
        return orderItems.stream()
                .reduce(0.0, (partialSum, orderItem) ->
                        (partialSum + orderItem.getQuantity() * orderItem.getUnitPrice()), Double::sum);
    }

    private Order prepareOrder(String userId, OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        order.setTotalCost(calculateTotalCostOfOrder(order.getOrderItems()));
        order.setPaymentStatus(PaymentStatus.IN_PROGRESS);
        order.setPaymentType(PaymentType.valueOf(orderRequest.getPaymentType()));
        order.setStatus(OrderStatus.NEW);
        return order;
    }
}
