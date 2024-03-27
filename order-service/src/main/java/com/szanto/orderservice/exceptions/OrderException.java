package com.szanto.orderservice.exceptions;

public class OrderException extends RuntimeException{

    public OrderException(String message) {
        super(message);
    }
}
