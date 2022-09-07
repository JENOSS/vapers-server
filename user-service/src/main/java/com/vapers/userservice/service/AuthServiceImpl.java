package com.vapers.userservice.service;

import com.vapers.userservice.dto.AuthDto;
import com.vapers.userservice.repository.AuthEntity;
import com.vapers.userservice.repository.AuthRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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
    public AuthDto.Token getAuthByUserName(String userName) {
        Optional<AuthEntity> auth = authRepository.findByUserName(userName);

        if(auth.isEmpty())
            throw new NoSuchElementException(userName);


        return mapper.map(auth, AuthDto.Token.class);
    }

    @Override
    public AuthDto.Token getAuthByToken(String token) {
        Optional<AuthEntity> auth = authRepository.findByRefreshToken(token);

        if(auth.isEmpty())
            throw new NoSuchElementException(token);


        return mapper.map(auth, AuthDto.Token.class);
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

        clearRefreshToken(userName);

        AuthDto.Token authToken = new AuthDto.Token(userName, refreshToken);
        AuthEntity authEntity = mapper.map(authToken, AuthEntity.class);
        authRepository.save(authEntity);

        return new AuthDto.responseCreate(accessToken, refreshToken);
    }

    @Override
    public Boolean isValidToken(String token, String userName) {
        return getAuthByToken(token).getUserName().equals(userName);
    }

    public void clearRefreshToken(String userName) {
        if(authRepository.findByUserName(userName).isPresent())
            authRepository.deleteAllByUserName(userName);
    }
}
