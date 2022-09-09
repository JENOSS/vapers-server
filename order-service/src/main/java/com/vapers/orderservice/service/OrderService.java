package com.vapers.orderservice.service;

import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.repository.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderDto.info createOrder(OrderDto.requestCreate requestCreate);
    OrderDto.info cancelOrder(Long id);
    List<OrderDto.info> getOrders();
    List<OrderDto.info> getOrdersByUserName(String userName);
    OrderDto.info getOrderById(Long id);
}
