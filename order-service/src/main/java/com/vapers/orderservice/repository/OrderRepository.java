package com.vapers.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Iterable<OrderEntity> findByUserName(String userName);
}
