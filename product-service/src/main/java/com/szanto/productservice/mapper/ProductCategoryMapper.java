package com.szanto.productservice.mapper;

import com.szanto.productservice.model.ProductCategoryRequest;
import com.szanto.productservice.model.ProductCategoryResponse;
import com.szanto.productservice.model.domain.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    @Mapping(target = "id", ignore = true)
    ProductCategory toEntity(ProductCategoryRequest productCategoryDto);
    ProductCategoryResponse toResponse(ProductCategory productCategory);
}
