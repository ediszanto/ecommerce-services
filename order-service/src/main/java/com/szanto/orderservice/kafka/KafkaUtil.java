package com.szanto.orderservice.kafka;

import com.szanto.orderservice.entity.Order;
import com.szanto.orderservice.entity.OrderItem;
import ecommerce.avro.AvroOrderItem;
import ecommerce.avro.AvroOrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    @Value("${kafka-topic.topic-name}")
    private String topicName;
    private final KafkaTemplate<String, AvroOrderMessage> kafkaTemplate;

    public boolean sendToKafka(String key, Order order) {
        AvroOrderMessage orderMessage = buildKafkaMessage(order);
        return kafkaTemplate.send(this.topicName, key, orderMessage).isDone();
    }

    public AvroOrderMessage buildKafkaMessage(Order order) {
        return AvroOrderMessage.newBuilder()
                .setUserId(order.getUserId())
                .setDate(order.getDate().toString())
                .setTotalCost(order.getTotalCost())
                .setShippingAddress(order.getShippingAddress())
                .setPaymentStatus(order.getPaymentStatus().name())
                .setPaymentType(order.getPaymentType().name())
                .setStatus(order.getStatus().name())
                .setOrderItems(getAvroOrderItems(order.getOrderItems()))
                .build();
    }

    private List<AvroOrderItem> getAvroOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::convertToAvroOrderItem)
                .toList();
    }

    private AvroOrderItem convertToAvroOrderItem(OrderItem orderItem) {
        return AvroOrderItem.newBuilder()
                .setProductId(orderItem.getProductId())
                .setQuantity(orderItem.getQuantity())
                .setUnitPrice(orderItem.getUnitPrice())
                .build();
    }
}
