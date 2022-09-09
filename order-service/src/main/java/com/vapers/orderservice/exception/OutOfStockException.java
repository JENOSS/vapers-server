package com.vapers.orderservice.exception;

import lombok.Getter;

@Getter
public class OutOfStockException extends RuntimeException{
    private ErrorCode code;

    public OutOfStockException(int stock){
        super("재고가 부족합니다. 남은 재고 : "+stock);
        this.code = ErrorCode.OUT_OF_STOCK_ERROR;
    }
}
