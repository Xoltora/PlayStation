package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.RoomTypeDto;
import com.example.playstationdemo.service.RoomTypeService;
import com.example.playstationdemo.utills.AppConst;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roomType")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    public RoomTypeController(@Lazy RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> save(@Valid @RequestBody RoomTypeDto dto){
        ApiResponse response = roomTypeService.save(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> edit(@Valid @RequestBody RoomTypeDto dto){
        ApiResponse response = roomTypeService.edit(dto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> remove(@PathVariable("id") Long id){
        ApiResponse response = roomTypeService.remove(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getAll(){
        ApiResponse response = roomTypeService.getAll();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> filter(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "price", required = false) Double price,
                                @RequestParam(value = "currentPage", required = false) Long currentPage,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = roomTypeService.filter(name, price, page, size, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
