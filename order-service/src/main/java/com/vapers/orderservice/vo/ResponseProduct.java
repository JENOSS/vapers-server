package com.vapers.orderservice.vo;

import lombok.Data;

@Data
public class ResponseProduct {
    private String name;
    private String productImage;
    private String description;
    private Integer category;
    private Integer price;
    private Integer stock;
}
