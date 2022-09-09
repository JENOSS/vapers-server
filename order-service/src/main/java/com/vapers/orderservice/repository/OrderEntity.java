package com.vapers.orderservice.repository;

import com.vapers.orderservice.dto.ProductDto;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table(name= "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Boolean isCanceled;

    public void changeCancel(boolean isCanceled){
        this.isCanceled = isCanceled;
    }

    public void setProduct(ProductDto product){
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
    }

    public void setTotalPrice(){
        this.totalPrice = unitPrice * qty;
    }



}
