package com.vapers.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrder {
    private Long id;
    private Long productId;
    private String userToken;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
}
