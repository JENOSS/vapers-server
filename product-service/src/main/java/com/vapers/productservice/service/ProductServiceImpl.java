package com.vapers.productservice.service;

import com.vapers.productservice.dto.ProductDto;
import com.vapers.productservice.repository.ProductEntity;
import com.vapers.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final Environment env;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, Environment env) {
        this.productRepository = productRepository;
        this.env = env;
    }

    @Override
    public void createProduct(ProductDto productDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);
        productRepository.save(productEntity);
    }


    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Iterable<ProductEntity> getProductByCategory(Integer category) {
        Iterable<ProductEntity> productEntities = productRepository.findAllByCategory(category);
        return productEntities;
    }

    @Override
    public Iterable<ProductEntity> getProductByNameContaining(String name) {
        List<ProductEntity> productEntities = productRepository.findByNameContainingIgnoreCase(name);
        return productEntities.isEmpty() ? new ArrayList<>() : productEntities;
    }

    @Override
    public Iterable<ProductEntity> getProductByAll() {
        return productRepository.findAll();
    }


    @Override
    public void updateProduct(Map<Object, Object> item) {
        Long id = Long.parseLong(String.valueOf(item.get("productId")));
        Integer qty = (Integer)item.get("qty");

        if(id == null || qty == null){
            throw new NullPointerException();
        }

        Optional<ProductEntity> entity = productRepository.findById(id);
        if(entity.isPresent()){
            ProductEntity productEntity = entity.get();
            productEntity.setStock(productEntity.getStock() - qty);
            productRepository.save(productEntity);
        }
    }
}
