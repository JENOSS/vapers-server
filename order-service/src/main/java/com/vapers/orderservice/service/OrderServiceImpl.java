package com.vapers.orderservice.service;

import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.repository.OrderEntity;
import com.vapers.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService{
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);
    }

    @Override
    public Iterable<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserToken(String userToken) {
        return orderRepository.findByUserToken(userToken);
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
