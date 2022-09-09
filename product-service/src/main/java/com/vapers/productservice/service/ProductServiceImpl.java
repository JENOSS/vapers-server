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
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              Environment env,
                              ModelMapper mapper) {
        this.productRepository = productRepository;
        this.env = env;
        this.mapper = mapper;
    }

    @Override
    public void createProduct(ProductDto.requestCreate productDto) {
        ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);
        productRepository.save(productEntity);
    }


    @Override
    public ProductDto.Info getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        return mapper.map(productEntity, ProductDto.Info.class);
    }

    @Override
    public List<ProductDto.Info> getProductsByCategory(Integer category) {
        Iterable<ProductEntity> productEntities = productRepository.findAllByCategory(category);

        List<ProductDto.Info> result = new ArrayList<>();
        productEntities.forEach(v->{
            result.add(mapper.map(v,ProductDto.Info.class));
        });

        return result;
    }

    @Override
    public List<ProductDto.Info> getProductsByNameContaining(String name) {
        Iterable<ProductEntity> productEntities = productRepository.findByNameContainingIgnoreCase(name);

        List<ProductDto.Info> result = new ArrayList<>();
        productEntities.forEach(v->{
            result.add(mapper.map(v,ProductDto.Info.class));
        });

        return result;
    }

    @Override
    public List<ProductDto.Info>  getAllProducts() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();

        List<ProductDto.Info> result = new ArrayList<>();
        productEntities.forEach(v->{
            result.add(mapper.map(v,ProductDto.Info.class));
        });

        return result;
    }


    @Override
    public void updateProduct(Map<Object, Object> item) {
        String stringId = String.valueOf(item.get("productId"));
        Integer qty = (Integer)item.get("qty");
        Boolean isCanceled = (Boolean)item.get("isCanceled");

        if(stringId == null || qty == null || isCanceled == null){
            throw new RuntimeException();
        }

        Long id = Long.parseLong(stringId);
        Optional<ProductEntity> entity = productRepository.findById(id);

        if(entity.isPresent()){
            ProductEntity productEntity = entity.get();
            if(isCanceled) {
                productEntity.changeStock(productEntity.getStock() + qty);
            }else{
                productEntity.changeStock(productEntity.getStock() - qty);
            }
            productRepository.save(productEntity);
        }
    }
}
