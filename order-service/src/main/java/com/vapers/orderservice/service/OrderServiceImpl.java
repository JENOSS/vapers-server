package com.vapers.orderservice.service;

import com.vapers.orderservice.client.ProductServiceClient;
import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.repository.OrderEntity;
import com.vapers.orderservice.repository.OrderRepository;
import com.vapers.orderservice.vo.ResponseProduct;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductServiceClient productServiceClient,
                            CircuitBreakerFactory circuitBreakerFactory) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        log.info("before call product-service");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        ResponseProduct product = circuitBreaker.run(() -> productServiceClient.getProduct(orderDto.getProductId()));
        log.info("after call product-service");

        orderDto.setUnitPrice(product != null ? product.getPrice() : -1);
        orderDto.setTotalPrice(product != null ? product.getPrice() * orderDto.getQty() : -1);
        orderDto.setProductName(product != null ? product.getName() : "");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        return orderDto;
    }

    @Override
    public OrderDto cancelOrder(Long id) {
        OrderEntity orderEntity = getOrderById(id);
        orderEntity.setIsCanceled(true);
        orderRepository.save(orderEntity);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper.map(orderEntity, OrderDto.class);
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
