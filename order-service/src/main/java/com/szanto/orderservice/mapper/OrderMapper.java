package com.szanto.orderservice.mapper;

import com.szanto.orderservice.entity.Order;
import com.szanto.orderservice.model.OrderRequest;
import com.szanto.orderservice.model.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "totalCost", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order toEntity(OrderRequest orderRequest);

    OrderResponse toResponse(Order order);
}
