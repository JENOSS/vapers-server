package com.vapers.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class OrderDto {
    @Getter
    public static class info{
        private Long id;
        private Long productId;
        private String userName;
        private String productName;
        private Integer qty;
        private Integer unitPrice;
        private Integer totalPrice;
        private Boolean isCanceled;
    }

    @Getter
    public static class requestCreate{
        private Long productId;
        private String userName;
        private Integer qty;
    }
    @Getter
    public static class produce{
        private Long productId;
        private Integer qty;
        private Boolean isCanceled;
    }
}
