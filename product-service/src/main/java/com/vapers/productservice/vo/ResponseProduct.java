package com.vapers.productservice.vo;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ResponseProduct {
    private Long id;
    private String name;
    private String productImage;
    private String description;
    private Integer category;
    private Integer price;
    private Integer stock;
}
