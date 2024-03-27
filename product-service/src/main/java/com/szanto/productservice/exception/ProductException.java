package com.szanto.productservice.exception;

public class ProductException extends RuntimeException{
    public ProductException(String message) {
        super(message);
    }
    public ProductException() {
        super("Requested product is not found!");
    }
}
