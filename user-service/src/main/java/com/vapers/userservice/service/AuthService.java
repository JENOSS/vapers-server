package com.vapers.userservice.service;

import com.vapers.userservice.dto.AuthDto;

public interface AuthService {
    AuthDto.Token getAuthByUserName(String userName);
    AuthDto.Token getAuthByToken(String token);

    AuthDto.responseCreate createToken(String userName);

    Boolean isValidToken(String token, String userName);
}
