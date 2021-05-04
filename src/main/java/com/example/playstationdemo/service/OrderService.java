package com.example.playstationdemo.service;

import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.OrderDetailDto;

import java.util.Date;
import java.util.List;

public interface OrderService {
    ApiResponse save(Long id);

    ApiResponse saveProduct(List<OrderDetailDto> dtoList);

    ApiResponse finish(Long id);

    ApiResponse result(Long id);

    ApiResponse detail(Long id);

    ApiResponse report(Date fromDate, Date toDate);

    ApiResponse reportDate(Date date);
}
