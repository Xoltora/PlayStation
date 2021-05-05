package com.example.playstationdemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;
    private long totalElements;
    private long totalPages;
    private long currentPage;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(String message, boolean success, Object data, long totalElements, long totalPages) {
        this.message = message;
        this.success = success;
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
