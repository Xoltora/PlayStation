package com.example.playstationdemo.service;

import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.CategoryDto;

public interface CategoryService {

    ApiResponse save(CategoryDto dto, User currentUser);

    ApiResponse edit(CategoryDto dto, User user);

    ApiResponse all(Integer page, Integer size);

    ApiResponse getById(Long id);

    ApiResponse remove(Long id);

    ApiResponse filter(String name, Integer page, Integer size, Long currentPage);
}
