package com.szanto.productservice.api;

import com.szanto.productservice.model.ProductCategoryRequest;
import com.szanto.productservice.model.ProductCategoryResponse;
import com.szanto.productservice.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@Component
@AllArgsConstructor
public class CategoryApiDelegateImpl implements CategoryApiDelegate{

    private ProductCategoryService productCategoryService;

    /**
     * POST /category : Add new category
     */
    @Override
    public ResponseEntity<ProductCategoryResponse> addProductCategory(ProductCategoryRequest productCategoryRequest) {
        return status(CREATED).body(productCategoryService.addCategory(productCategoryRequest));
    }

    /**
     * DELETE /category/{categoryId} : Get category by id
     */
    @Override
    public ResponseEntity<Void> deleteProductCategory(Long categoryId) {
        return null;
    }

    /**
     * GET /category/{categoryId} : Get category by id
     */
    @Override
    public ResponseEntity<ProductCategoryResponse> getProductCategory(Long categoryId) {
        return null;
    }
}
