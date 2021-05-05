package com.example.playstationdemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResToken {
    private String type = "Bearer ";
    private String token;

    public ResToken(String token) {
        this.token = token;
    }
}
