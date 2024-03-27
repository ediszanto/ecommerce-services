package com.szanto.productservice.mapper;

import com.szanto.productservice.model.ProductRequest;
import com.szanto.productservice.model.ProductResponse;
import com.szanto.productservice.model.domain.Product;
import com.szanto.productservice.model.domain.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "quantity", source = "product.quantity")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "category.id", source = "productCategory.id")
    @Mapping(target = "category.name", source = "productCategory.name")
    ProductResponse toResponse(Product product, ProductCategory productCategory);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequest productRequest);
}
