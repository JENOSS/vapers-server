package com.vapers.orderservice.service;

import com.vapers.orderservice.client.ProductServiceClient;
import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.dto.ProductDto;
import com.vapers.orderservice.exception.OutOfStockException;
import com.vapers.orderservice.repository.OrderEntity;
import com.vapers.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductServiceClient productServiceClient,
                            CircuitBreakerFactory circuitBreakerFactory,
                            ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.mapper = mapper;
    }

    @Override
    public OrderDto.info createOrder(OrderDto.requestCreate requestCreate) {

        log.info("before call product-service");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        ProductDto product = circuitBreaker.run(() -> productServiceClient.getProduct(requestCreate.getProductId()),
                throwable -> {throw new RuntimeException();} );
        log.info("after call product-service");

        if(product.getStock() - requestCreate.getQty() <= 0) throw new OutOfStockException(product.getStock());

        OrderEntity orderEntity = mapper.map(requestCreate, OrderEntity.class);
        orderEntity.setProduct(product);
        orderEntity.setTotalPrice();
        orderEntity.changeCancel(false);

        orderRepository.save(orderEntity);

        return mapper.map(orderEntity, OrderDto.info.class);
    }

    @Override
    public OrderDto.info cancelOrder(Long id) {
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException();
        });

        entity.changeCancel(true);
        orderRepository.save(entity);

        return mapper.map(entity, OrderDto.info.class);
    }

    @Override
    public List<OrderDto.info> getOrders() {
        Iterable<OrderEntity> entities = orderRepository.findAll();

        List<OrderDto.info> result = new ArrayList<>();
        entities.forEach(v -> {
            result.add(mapper.map(v, OrderDto.info.class));
        });

        return result;
    }

    @Override
    public List<OrderDto.info> getOrdersByUserName(String userName) {
        Iterable<OrderEntity> entities = orderRepository.findByUserName(userName);

        List<OrderDto.info> result = new ArrayList<>();
        entities.forEach(v -> {
            result.add(mapper.map(v, OrderDto.info.class));
        });

        return result;
    }

    @Override
    public OrderDto.info getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException();
        });

        return mapper.map(entity, OrderDto.info.class);
    }
}
