package com.vapers.productservice.service;

import com.vapers.productservice.dto.ProductDto;
import com.vapers.productservice.repository.ProductEntity;

import java.util.Map;

public interface ProductService {
    void createProduct(ProductDto productDto);
    void updateProduct(Map<Object, Object> map);

    ProductEntity getProductById(Long id);
    Iterable<ProductEntity> getProductByCategory(Integer category);
    Iterable<ProductEntity> getProductByNameContaining(String name);
    Iterable<ProductEntity> getProductByAll();

}
