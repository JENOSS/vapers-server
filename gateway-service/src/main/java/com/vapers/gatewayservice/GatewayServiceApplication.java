package com.vapers.gatewayservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vapers.gatewayservice.exception.ExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public HttpTraceRepository httpTraceRepository() { return new InMemoryHttpTraceRepository(); }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
