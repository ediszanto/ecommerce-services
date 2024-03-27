package com.szanto.productservice.service;

import com.szanto.productservice.exception.ProductException;
import com.szanto.productservice.exception.ProductCategoryNotFound;
import com.szanto.productservice.mapper.ProductMapper;
import com.szanto.productservice.model.DecreaseProductQuantityRequest;
import com.szanto.productservice.model.ProductRequest;
import com.szanto.productservice.model.ProductResponse;
import com.szanto.productservice.model.domain.Product;
import com.szanto.productservice.model.domain.ProductCategory;
import com.szanto.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) {
        ProductCategory productCategory = verifyCategoryExistence(productRequest.getCategoryId());
        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new ProductException("Product already present in database!");
        }
        Product product = productMapper.toEntity(productRequest);
        product.setCategoryId(productCategory.getId());
        productRepository.save(product);

        return productMapper.toResponse(product, productCategory);
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.getAll().orElseThrow(ProductException::new);
        return products.stream().map(product -> productMapper.toResponse(product, new ProductCategory(product.getCategoryId(), ""))).toList();
    }

    @Transactional
    public Boolean decreaseQuantity(DecreaseProductQuantityRequest request) {
        Product product = getById(request.getProductId());
        if (product.getQuantity() < request.getProductNumber()) {
            throw new ProductException("Decreasing the requested quantity results a negative quantity. You can't decrease that many items!");
        }

        product.setQuantity(product.getQuantity() - request.getProductNumber());
        productRepository.save(product);

        return Boolean.TRUE;
    }

    private ProductCategory verifyCategoryExistence(int id) {
        return productCategoryService.findCategoryById(id).orElseThrow(ProductCategoryNotFound::new); // nu tre sa faci throw la ceva din alt sevice...
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductException::new);
    }

    public void deleteProduct(Long productId) {
        Product product = getById(productId);
        productRepository.delete(product);
    }

    public ProductResponse updateProduct(ProductRequest request) {
//        productRepository.
        return null;
    }
}
