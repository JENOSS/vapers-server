package com.vapers.userservice.controller;

import com.vapers.userservice.dto.AuthDto;
import com.vapers.userservice.service.AuthService;
import com.vapers.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Environment env;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Autowired
    public AuthController(Environment env, AuthService authService, ModelMapper mapper) {
        this.env = env;
        this.authService = authService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    public ResponseEntity<AuthDto.responseCreate> createToken(AuthDto.requestCreate request){
        AuthDto.responseCreate result = authService.createToken(request.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


}
