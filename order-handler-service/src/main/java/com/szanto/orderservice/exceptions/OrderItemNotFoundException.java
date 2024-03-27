package com.szanto.orderservice.exceptions;


public class OrderItemNotFoundException extends RuntimeException{

    public OrderItemNotFoundException() {
        super("This orderId does not have orderItems");
    }
}
