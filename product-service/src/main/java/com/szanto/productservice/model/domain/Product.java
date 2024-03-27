package com.szanto.productservice.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", schema = "product_schema")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name is mandatory")
    @Size(max = 50)
    private String name;

    @NotNull (message = "Quantity is mandatory")
    @Min(value = 0L, message = "Quantity should be a positive number")
    private Long quantity;

    @NotNull (message = "Price is mandatory")
    @Min(value = 0, message = "Price should be a positive number")
    private Float price;

    @NotNull (message = "Category ID is mandatory")
    @Positive(message = "CategoryId must be an positive integer")
    private Integer categoryId;
}
