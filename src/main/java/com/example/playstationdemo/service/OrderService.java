package com.example.playstationdemo.service;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.OrderDetailDto;

import java.util.Date;
import java.util.List;

public interface OrderService {
    ApiResponse save(Long id);

    ApiResponse saveProduct(List<OrderDetailDto> dtoList);

    ApiResponse finish(Long id);

    ApiResponse result(Long id);

    ApiResponse detail(Long id);

    ApiResponse report(Date fromDate, Date toDate, Integer size, Integer page, Long currentPage);

    ApiResponse findAll(Date date, Integer page, Integer size, Integer currentPage);
}
