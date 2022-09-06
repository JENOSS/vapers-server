package com.vapers.orderservice.client;

import com.vapers.orderservice.vo.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    ResponseProduct getProduct(@PathVariable("id") Long id);
}
