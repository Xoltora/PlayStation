package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.OrderDetailDto;
import com.example.playstationdemo.payload.OrderDto;
import com.example.playstationdemo.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/start/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> start(@PathVariable("id") Long id){
        ApiResponse response = orderService.save(id);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/products")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> saveProduct(@RequestBody List<OrderDetailDto> dtoList){
        ApiResponse response = orderService.saveProduct(dtoList);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/finish/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> finish(@PathVariable("id") Long id){
        ApiResponse response = orderService.finish(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> result(@PathVariable("id") Long id){
        ApiResponse response = orderService.result(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> details(@PathVariable("id") Long id){
        ApiResponse response = orderService.detail(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

//    @GetMapping("/report")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
//    public HttpEntity<?> report(@RequestParam(value = "fromDate", required = false)Date fromDate,
//                                @RequestParam(value = "toDate", required = false) Date toDate){
//        ApiResponse response = orderService.report(fromDate, toDate);
//    }


}
