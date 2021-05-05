package com.example.playstationdemo.service;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.RoomTypeDto;

public interface RoomTypeService {
    ApiResponse save(RoomTypeDto dto);

    ApiResponse edit(RoomTypeDto dto);

    ApiResponse remove(Long id);

    ApiResponse getAll();

    ApiResponse filter(String name, Double price, Integer page, Integer size, Long currentPage);
}
