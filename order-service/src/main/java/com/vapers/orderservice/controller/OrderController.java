package com.vapers.orderservice.controller;

import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.messagequeue.KafkaProducer;
import com.vapers.orderservice.repository.OrderEntity;
import com.vapers.orderservice.service.OrderService;
import com.vapers.orderservice.vo.RequestOrderCreate;
import com.vapers.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {
    private Environment env;
    private OrderService orderService;
    private KafkaProducer kafkaProducer;

    @Autowired
    public OrderController(Environment env,
                           OrderService orderService,
                           KafkaProducer kafkaProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@RequestBody RequestOrderCreate requestOrderCreate){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(requestOrderCreate, OrderDto.class);
        orderDto.setIsCanceled(false);

        orderService.createOrder(orderDto);
        /* kafka */
        kafkaProducer.send("vapers-order-topic", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(){
        Iterable<OrderEntity> orderList = orderService.getOrders();

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/orders/{userToken}")
    public ResponseEntity<List<ResponseOrder>> getOrdersByUserToken(@PathVariable("userToken") String userToken){
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserToken(userToken);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
