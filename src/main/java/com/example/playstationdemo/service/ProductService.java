package com.example.playstationdemo.service;

import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.ProductDto;

public interface ProductService {
    ApiResponse save(ProductDto dto);

    ApiResponse edit(ProductDto dto);

    ApiResponse remove(Long id);

    ApiResponse getAll(Integer page, Integer size);

    ApiResponse getById(Long id);

    ApiResponse filter(String name, Double fromPrice, Double toPrice, Long categoryId, Integer page, Integer size, Long currentPage);
}
