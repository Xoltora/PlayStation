package com.example.playstationdemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignIn {
    @NotBlank(message = "username is required")
    String username;

    @NotBlank(message = "password is required")
//    @Size(min = 8, max = 15, message = "password length should be at least min = 5 and max = 15")
    String password;
}
