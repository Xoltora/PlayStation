package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.response.ResToken;
import com.example.playstationdemo.payload.request.SignInRequest;
import com.example.playstationdemo.service.impl.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public HttpEntity<?> login(@Valid @RequestBody SignInRequest signInRequest){
        ResToken resToken = authService.signIn(signInRequest);
        return ResponseEntity.status(resToken != null ? 200 : 401).body(resToken);
    }
}
