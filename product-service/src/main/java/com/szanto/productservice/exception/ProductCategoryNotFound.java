package com.szanto.productservice.exception;

public class ProductCategoryNotFound extends RuntimeException{
    public ProductCategoryNotFound() {
        super("Invalid Product Category!");
    }

    public ProductCategoryNotFound(String message) {
        super(message);
    }
}
