package com.szanto.orderservice.mapper;

import com.szanto.orderservice.entity.domain.OrderItem;
import ecommerce.avro.AvroOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(AvroOrderItem avroOrderItem);
}
