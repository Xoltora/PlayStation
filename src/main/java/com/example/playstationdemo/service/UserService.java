package com.example.playstationdemo.service;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.UserDto;

public interface UserService {
    ApiResponse save(UserDto userDto);

    ApiResponse edit(UserDto userDto);

    ApiResponse all(Integer page, Integer size);

    ApiResponse getById(Long id);

    ApiResponse removeById(Long id);

    ApiResponse changePassword(String login, String password);

    ApiResponse filter(String fio, Integer page, Integer size);
}
