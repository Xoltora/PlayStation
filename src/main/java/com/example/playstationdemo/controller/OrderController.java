package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.OrderDetailDto;
import com.example.playstationdemo.service.OrderService;
import com.example.playstationdemo.utills.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

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

//    @GetMapping("/{id}")
    @GetMapping("/result/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> result(@PathVariable("id") Long id){
        ApiResponse response = orderService.result(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> details(@PathVariable("id") Long id){
        ApiResponse response = orderService.detail(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/report")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> report(@RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                @RequestParam(value = "currentPage", required = false) Long currentPage){
        ApiResponse response = orderService.report(fromDate, toDate, size, page, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> reportDate(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                    @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                    @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size,
                                    @RequestParam(value = "currentPage", required = false) Integer currentPage){
        ApiResponse response = orderService.findAll(date, page, size, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

//    @GetMapping("/reportDetail")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")

}
