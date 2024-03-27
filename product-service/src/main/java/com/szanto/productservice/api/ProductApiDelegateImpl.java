package com.szanto.productservice.api;

import com.szanto.productservice.model.DecreaseProductQuantityRequest;
import com.szanto.productservice.model.ProductRequest;
import com.szanto.productservice.model.ProductResponse;
import com.szanto.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Component
@RequiredArgsConstructor
public class ProductApiDelegateImpl implements ProductApiDelegate {

    private final ProductService productService;

    /**
     * POST /product : Add a new Product
     */
    @Override
    public ResponseEntity<ProductResponse> addProduct(ProductRequest productRequest) {
        ProductResponse response = productService.addProduct(productRequest);
        return status(CREATED).body(response);
    }

    /**
     * GET /product/{productId} : Get a product by id
     */
    @Override
    public ResponseEntity<ProductResponse> getProduct(Long productId) {
        return null;
    }

    /**
     * GET /product : Get All products
     */
    @Override
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return status(OK).body(productService.getAll());
    }

    /**
     * PUT /product/{productId} : Update product by id
     */
    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long productId, ProductRequest productRequest) {
        return status(OK).body(productService.updateProduct(productRequest));
    }

    /**
     * DELETE /product/{productId} : Delete product by product productId
     */
    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        productService.deleteProduct(productId);
        return status(OK).build();
    }

    /**
     * PATCH /product/decreaseQuantity : Decrease product decreaseQuantity
     */
    @Override
    public ResponseEntity<Void> decreaseProductQuantity(DecreaseProductQuantityRequest decreaseProductQuantityRequest) {
        Boolean response = productService.decreaseQuantity(decreaseProductQuantityRequest);
        return Boolean.TRUE.equals(response) ? status(OK).build() : status(EXPECTATION_FAILED).build();
    }
}
