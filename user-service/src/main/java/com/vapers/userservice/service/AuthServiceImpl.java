package com.vapers.userservice.service;

import com.vapers.userservice.dto.AuthDto;
import com.vapers.userservice.exception.UnauthorizedException;
import com.vapers.userservice.repository.AuthEntity;
import com.vapers.userservice.repository.AuthRepository;
import io.jsonwebtoken.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;
    private final ModelMapper mapper;
    private final Environment env;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository,
                           ModelMapper mapper,
                           Environment env) {
        this.authRepository = authRepository;
        this.mapper = mapper;
        this.env = env;
    }

    @Override
    public List<AuthDto.Token> getAllTokens() {
        Iterable<AuthEntity> authEntities = authRepository.findAll();
        List<AuthDto.Token> result = new ArrayList<>();
        authEntities.forEach(v->{
            result.add(mapper.map(v,AuthDto.Token.class));
        });
        return result;
    }

    @Override
    public AuthDto.responseCreate createToken(String userName) {
        String accessToken = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.access_expiration_time"))))
                .signWith(SignatureAlgorithm.HS256, env.getProperty("token.secret"))
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.refresh_expiration_time"))))
                .signWith(SignatureAlgorithm.HS256, env.getProperty("token.secret"))
                .compact();

        AuthEntity authEntity = authRepository.findByUserName(userName).orElse(new AuthEntity());
        authEntity.changeUserName(userName);
        authEntity.changeRefreshToken(refreshToken);

        authRepository.save(authEntity);
        return new AuthDto.responseCreate(accessToken, refreshToken);
    }

    @Override
    public AuthDto.responseCreate recreateToken(AuthDto.requestCreate request){
        if(!isValidToken(request.getUserName(), request.getRefreshToken()))
            throw new UnauthorizedException();

        return createToken(request.getUserName());
    }

    public Boolean isValidToken(String userName, String token) {
        Optional<AuthEntity> entity = authRepository.findByUserName(userName);
        return entity.map(authEntity -> authEntity.getRefreshToken().equals(token)).orElse(true);
    }

}
