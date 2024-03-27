package com.szanto.orderservice.service;

import com.szanto.orderservice.entity.OrderStatus;
import com.szanto.orderservice.entity.PaymentStatus;
import com.szanto.orderservice.entity.domain.Order;
import com.szanto.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(Order order) {
        // pay, decrease product stock, update order
        Order savedOrder = orderRepository.save(order);
        pay(order);
        decreaseProductStock(savedOrder);
        updateOrderStatus(savedOrder);
    }

    public boolean decreaseProductStock(Order order) {
        // bla bla
        return true;
    }

    private void pay(Order order) {
        log.info("Order with orderId: {} is paid", order.getId());
    }

    private Order updateOrderStatus(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentStatus(PaymentStatus.SUCCESS);

        return orderRepository.save(order);
    }
}
