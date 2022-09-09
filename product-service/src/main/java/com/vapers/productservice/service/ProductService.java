package com.vapers.productservice.service;

import com.vapers.productservice.dto.ProductDto;
import com.vapers.productservice.repository.ProductEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    void createProduct(ProductDto.requestCreate productDto);
    void updateProduct(Map<Object, Object> map);

    ProductDto.Info getProductById(Long id);
    List<ProductDto.Info> getProductsByCategory(Integer category);
    List<ProductDto.Info> getProductsByNameContaining(String name);
    List<ProductDto.Info> getAllProducts();

}
