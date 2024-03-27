package com.szanto.orderservice.consumer;

import com.szanto.orderservice.entity.domain.Order;
import com.szanto.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {


    private final OrderService orderService;
    private final KafkaMessageDeserializer kafkaMessageDeserializer;

    @KafkaListener(groupId = "test", clientIdPrefix = "test", topics = "${kafka-topic.topic-name}", batch = "false")
    public void consumeOrder(ConsumerRecord<String, GenericRecord> kafkaRecord) {
        Order order = kafkaMessageDeserializer.parseAvroMessage(kafkaRecord.value());
        orderService.placeOrder(order);
    }

}
