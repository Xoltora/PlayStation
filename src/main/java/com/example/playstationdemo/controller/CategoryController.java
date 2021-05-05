package com.example.playstationdemo.controller;

import com.example.playstationdemo.entity.User;
import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.CategoryDto;
import com.example.playstationdemo.secret.CurrentUser;
import com.example.playstationdemo.service.CategoryService;
import com.example.playstationdemo.utills.AppConst;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> save(@Valid @RequestBody CategoryDto dto, @CurrentUser User user){
        ApiResponse response = categoryService.save(dto, user);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> edit(@Valid @RequestBody CategoryDto dto, @CurrentUser User user){
        ApiResponse response = categoryService.edit(dto, user);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> all(@RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                             @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = categoryService.all(page, size);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getById(@PathVariable("id") Long id){
        ApiResponse response = categoryService.getById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> remove(@PathVariable("id") Long id){
        ApiResponse response = categoryService.remove(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> filter(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "currentPage", required = false) Long currentPage,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = categoryService.filter(name, page, size, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
