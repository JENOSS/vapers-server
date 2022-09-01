package com.vapers.productservice.dto;

import io.micrometer.core.lang.Nullable;
import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {
    private String name;
    private String productImage;
    private String description;
    private Integer category;
    private Integer price;
    private Integer stock;
}
