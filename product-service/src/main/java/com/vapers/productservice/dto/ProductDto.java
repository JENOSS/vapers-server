package com.vapers.productservice.dto;

import io.micrometer.core.lang.Nullable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

public class ProductDto {

    @Getter
    public static class Info{
        private Long id;
        private String name;
        private String productImage;
        private String description;
        private Integer category;
        private Integer price;
        private Integer stock;
    }

    @Getter
    public static class requestCreate{
        private String name;
        private String productImage;
        private String description;
        private Integer category;
        private Integer price;
        private Integer stock;
    }

}
