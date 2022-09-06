package com.vapers.orderservice.service;

import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.repository.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto cancelOrder(Long id);
    Iterable<OrderEntity> getOrders();
    Iterable<OrderEntity> getOrdersByUserToken(String userToken);
    OrderEntity getOrderById(Long id);
}
