package com.vapers.productservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vapers.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    private final ProductService productService;

    @Autowired
    public KafkaConsumer(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "vapers-order-topic")
    public void updateQty(String kafkaMessage){
        log.info("Kafka Message: -> "+ kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        productService.updateProduct(map);

    }
}
