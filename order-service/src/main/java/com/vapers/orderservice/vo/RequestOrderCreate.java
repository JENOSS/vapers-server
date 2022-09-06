package com.vapers.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrderCreate {
    private Long productId;
    private String userToken;
    private Integer qty;
}
