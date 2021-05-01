package com.example.playstationdemo.payload;

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
