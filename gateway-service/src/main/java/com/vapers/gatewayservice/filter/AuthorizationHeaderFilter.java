package com.vapers.gatewayservice.filter;

import com.vapers.gatewayservice.exception.ErrorCode;
import com.vapers.gatewayservice.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            List<String> headers = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
            if(headers == null)
                throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_ERROR);

            String authorizationHeader = headers.get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt))
                throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_ERROR);


            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody()
                    .getSubject();

        } catch (Exception ex){
            returnValue = false;
        }


        if (subject == null || subject.isEmpty()) returnValue = false;


        return returnValue;
    }

}
