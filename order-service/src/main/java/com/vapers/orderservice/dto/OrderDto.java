package com.vapers.orderservice.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Boolean isCanceled;
    private String userToken;
}
