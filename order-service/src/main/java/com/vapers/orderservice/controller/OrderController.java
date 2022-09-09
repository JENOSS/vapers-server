package com.vapers.orderservice.controller;

import com.vapers.orderservice.dto.OrderDto;
import com.vapers.orderservice.messagequeue.KafkaProducer;
import com.vapers.orderservice.repository.OrderEntity;
import com.vapers.orderservice.service.OrderService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {
    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(Environment env,
                           OrderService orderService,
                           KafkaProducer kafkaProducer,
                           ModelMapper mapper) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.mapper = mapper;
    }

    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto.requestCreate requestOrderCreate){
        OrderDto.info newOrder = orderService.createOrder(requestOrderCreate);
        OrderDto.produce produce = mapper.map(newOrder, OrderDto.produce.class);

        /* kafka */
        kafkaProducer.send("vapers-order-topic", produce);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto.info>> getOrders(HttpServletRequest request){
        String userName = request.getParameter("userName");

        if(userName != null){
            return getOrdersByUserName(userName);
        }else{
            List<OrderDto.info> orderList = orderService.getOrders();
            return ResponseEntity.status(HttpStatus.OK).body(orderList);
        }

    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDto.info> getOrderById(@PathVariable("id") Long id){
        OrderDto.info order = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    public ResponseEntity<List<OrderDto.info>> getOrdersByUserName(String userName){
        List<OrderDto.info> order = orderService.getOrdersByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/order/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") Long id){
        OrderDto.info orderDto = orderService.cancelOrder(id);
        OrderDto.produce produce = mapper.map(orderDto, OrderDto.produce.class);
        /* kafka */
        kafkaProducer.send("vapers-order-topic", produce);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
