package com.example.playstationdemo.service;

import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.RoomDto;

public interface RoomService {
    ApiResponse save(RoomDto dto);

    ApiResponse edit(RoomDto dto);

    ApiResponse getRoom(Integer page, Integer size);

    ApiResponse getById(Long id);

    ApiResponse remove(Long id);

    ApiResponse filter(String name, Long typeId, State state, Integer page, Integer size, Long currentPage);
}
