package com.szanto.productservice.exception;

public class InvalidProductCategoryException extends RuntimeException{
    public InvalidProductCategoryException(String message) {
        super(message);
    }
}
