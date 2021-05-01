package com.example.playstationdemo.controller;

import com.example.playstationdemo.entity.enums.State;
import com.example.playstationdemo.payload.ApiResponse;
import com.example.playstationdemo.payload.RoomDto;
import com.example.playstationdemo.service.RoomService;
import com.example.playstationdemo.utills.AppConst;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> save(@Valid @RequestBody RoomDto dto) {
        ApiResponse response = roomService.save(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> edit(@Valid @RequestBody RoomDto dto) {
        ApiResponse response = roomService.edit(dto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getRoom(@RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                 @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size) {
        ApiResponse response = roomService.getRoom(page, size);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getById(@PathVariable("id") Long id) {
        ApiResponse response = roomService.getById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> remove(@PathVariable("id") Long id){
        ApiResponse response = roomService.remove(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> filter(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "typeId", required = false) Long typeId,
                                @RequestParam(value = "state", required = false) State state,
                                @RequestParam(value = "staet", required = false) Long currentPage,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = roomService.filter(name, typeId, state, page, size, currentPage);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
