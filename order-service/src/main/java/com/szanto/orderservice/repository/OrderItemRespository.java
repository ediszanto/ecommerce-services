package com.szanto.orderservice.repository;

import com.szanto.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRespository extends JpaRepository<OrderItem, Long> {
    Optional<List<OrderItem>> findByOrderId(Long orderId);
}
