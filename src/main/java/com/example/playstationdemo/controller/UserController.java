package com.example.playstationdemo.controller;

import com.example.playstationdemo.payload.response.ApiResponse;
import com.example.playstationdemo.payload.dto.UserDto;
import com.example.playstationdemo.service.UserService;
import com.example.playstationdemo.utills.AppConst;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> save(@Valid @RequestBody UserDto userDto){
        ApiResponse response = userService.save(userDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> edit(@Valid @RequestBody UserDto userDto){
        ApiResponse response = userService.edit(userDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> getById(@PathVariable(value = "id") Long id){
        ApiResponse response = userService.getById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> get(@RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                             @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = userService.all(page, size);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<?> removeById(@PathVariable("id") Long id){
        ApiResponse response = userService.removeById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> changePassword(@RequestParam(value = "login") String login,
                                        @RequestParam(value = "password") String password){
        ApiResponse response = userService.changePassword(login, password);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public HttpEntity<?> filter(@RequestParam(value = "fio", required = false) String fio,
                                @RequestParam(value = "page", defaultValue = AppConst.PAGE_DEFAULT_NUMBER) Integer page,
                                @RequestParam(value = "size", defaultValue = AppConst.PAGE_DEFAULT_SIZE) Integer size){
        ApiResponse response = userService.filter(fio, page, size);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
