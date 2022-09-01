package com.vapers.productservice.repository;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String productImage;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer price;
}
