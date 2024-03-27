package com.szanto.productservice.service;

import com.szanto.productservice.exception.InvalidProductCategoryException;
import com.szanto.productservice.mapper.ProductCategoryMapper;
import com.szanto.productservice.model.ProductCategoryRequest;
import com.szanto.productservice.model.ProductCategoryResponse;
import com.szanto.productservice.model.domain.ProductCategory;
import com.szanto.productservice.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;
    public ProductCategoryResponse addCategory(ProductCategoryRequest productCategoryRequest) {
         if (productCategoryRepository.findByName(productCategoryRequest.getName()).isPresent()) {
             throw new InvalidProductCategoryException("Category already present!");
         }
        ProductCategory entity = productCategoryMapper.toEntity(productCategoryRequest);
        ProductCategory result = productCategoryRepository.save(entity);
        return productCategoryMapper.toResponse(result);
    }

    public Optional<ProductCategory> findCategoryById(int id) {
        return productCategoryRepository.findById(id);
    }
}
