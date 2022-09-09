package com.vapers.userservice.service;

import com.vapers.userservice.dto.AuthDto;

import java.util.List;

public interface AuthService {
    List<AuthDto.Token> getAllTokens();
    AuthDto.responseCreate createToken(String userName);
    AuthDto.responseCreate recreateToken(AuthDto.requestCreate request);

}
