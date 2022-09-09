package com.vapers.productservice.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    Iterable<ProductEntity> findAllByCategory(Integer category);
    Iterable<ProductEntity>  findByNameContainingIgnoreCase(String name);
}
