package com.szanto.orderservice.entity.domain;

import com.szanto.orderservice.entity.OrderStatus;
import com.szanto.orderservice.entity.PaymentStatus;
import com.szanto.orderservice.entity.PaymentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "order_schema")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private LocalDateTime date;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;

}
