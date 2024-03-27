package com.szanto.orderservice.consumer;

import com.szanto.orderservice.entity.OrderStatus;
import com.szanto.orderservice.entity.PaymentStatus;
import com.szanto.orderservice.entity.PaymentType;
import com.szanto.orderservice.entity.domain.Order;
import com.szanto.orderservice.mapper.OrderItemMapper;
import ecommerce.avro.AvroOrderItem;
import ecommerce.avro.AvroOrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageDeserializer {

    private final OrderItemMapper orderItemMapper;

    public Order parseAvroMessage(GenericRecord kafkaRecord) {
        AvroOrderMessage avroOrderMessage = new AvroOrderMessage(
                kafkaRecord.get("userId").toString(),
                kafkaRecord.get("date").toString(),
                Double.parseDouble(kafkaRecord.get("totalCost").toString()),
                kafkaRecord.get("shippingAddress").toString(),
                kafkaRecord.get("paymentStatus").toString(),
                kafkaRecord.get("paymentType").toString(),
                kafkaRecord.get("status").toString(),
                parseAvroOrderItems(kafkaRecord)

        );
        log.info("Kafka message received: {}", avroOrderMessage);
        return mapToOrder(avroOrderMessage);
    }

    public Order mapToOrder(AvroOrderMessage avroOrderMessage) {
        Order order = new Order();
        order.setUserId(avroOrderMessage.getUserId().toString());
        order.setDate(LocalDateTime.parse(avroOrderMessage.getDate().toString()));
        order.setTotalCost(avroOrderMessage.getTotalCost());
        order.setShippingAddress(avroOrderMessage.getShippingAddress().toString());
        order.setPaymentStatus(PaymentStatus.valueOf(avroOrderMessage.getPaymentStatus().toString()));
        order.setPaymentType(PaymentType.valueOf(avroOrderMessage.getPaymentType().toString()));
        order.setStatus(OrderStatus.valueOf(avroOrderMessage.getStatus().toString()));
        order.setOrderItems(avroOrderMessage.getOrderItems().stream().map(orderItemMapper::toEntity).toList());
        order.getOrderItems().forEach(orderItem -> orderItem.setOrder(order));

        return order;
    }

    public List<AvroOrderItem> parseAvroOrderItems(GenericRecord genericRecord) {
        List<GenericRecord> genericOrderItems = (List<GenericRecord>) genericRecord.get("orderItems");
        return genericOrderItems.stream().map(this::parseGenericOrderItem).toList();
    }

    public AvroOrderItem parseGenericOrderItem(GenericRecord genericOrderItem) {
        return new AvroOrderItem(
                Long.parseLong(genericOrderItem.get("productId").toString()),
                Long.parseLong(genericOrderItem.get("quantity").toString()),
                Double.parseDouble(genericOrderItem.get("unitPrice").toString())
        );
    }
}
