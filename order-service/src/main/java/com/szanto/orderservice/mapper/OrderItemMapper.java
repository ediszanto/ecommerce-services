package com.szanto.orderservice.mapper;

import com.szanto.orderservice.entity.OrderItem;
import com.szanto.orderservice.model.OrderItemExternal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemExternal orderItem);

    OrderItemExternal toDto(OrderItem orderItem);
}
