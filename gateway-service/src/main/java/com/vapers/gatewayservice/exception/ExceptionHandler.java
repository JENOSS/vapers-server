package com.vapers.gatewayservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(-1)
public class ExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper;

    @Autowired
    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = null;
        if(ex.getClass() == ResponseStatusException.class){
            errorResponse = new ErrorResponse(null, ErrorCode.FRAME_WORK_INTERNAL_ERROR);
        }else if(ex.getClass() == NotFoundException.class){
            errorResponse = new ErrorResponse(null, ErrorCode.SERVICE_UNABLE);
        }else if(ex.getClass() == UnauthorizedException.class){
            errorResponse = new ErrorResponse(null, ErrorCode.UNAUTHORIZED_ERROR);
        }else if(ex.getClass().getSuperclass() == ConnectException.class){
            errorResponse = new ErrorResponse(null, ErrorCode.CONNECTION_REFUSED);
        }else{
            errorResponse = new ErrorResponse(null, ErrorCode.UNDEFINED_ERROR);
        }

        response.setStatusCode(HttpStatus.valueOf(errorResponse.getStatus()));

        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(objectMapper);
        return response.writeWith(
                encoder.encode(
                        Mono.just(errorResponse),
                        response.bufferFactory(),
                        ResolvableType.forInstance(errorResponse),
                        MediaType.APPLICATION_JSON,
                        Hints.from(Hints.LOG_PREFIX_HINT, exchange.getLogPrefix())
                ));

    }
}
