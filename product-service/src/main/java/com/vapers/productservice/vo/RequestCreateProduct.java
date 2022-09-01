package com.vapers.productservice.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestCreateProduct {
    @NotNull(message = "name cannot be null")
    @Size(min = 1, message = "name not be less than one characters")
    private String name;

    @NotNull(message = "stock cannot be null")
    private Integer stock;

    private String productImage;

    private String description;

    @NotNull(message = "category cannot be null")
    private Integer category;

    @NotNull(message = "price cannot be null")
    private Integer price;
}
