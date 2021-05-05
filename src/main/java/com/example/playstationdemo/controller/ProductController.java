package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.ProductDto;
import com.example.playstationdemo.service.ProductService;
import com.example.playstationdemo.utills.AppConst;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> save(@Valid @RequestBody ProductDto dto){
        ApiResponse response = productService.save(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> editProduct(@Valid @RequestBody ProductDto dto){
        ApiResponse response = productService.edit(dto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> remove(@PathVariable("id") Long id){
        ApiResponse response = productService.remove(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> allProduct(@RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                    @RequestParam(value = "szie", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = productService.getAll(page, size);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getById(@PathVariable("id") Long id){
        ApiResponse response = productService.getById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> filter(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "fromPrice", required = false) Double fromPrice,
                                @RequestParam(value = "toPrice", required = false) Double toPrice,
                                @RequestParam(value = "categoryId", required = false) Long categoryId,
                                @RequestParam(value = "currentPage", required = false) Long currentPage,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer  page,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = productService.filter(name, fromPrice, toPrice, categoryId, page, size, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
