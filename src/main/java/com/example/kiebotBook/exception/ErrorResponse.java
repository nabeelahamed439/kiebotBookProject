package com.example.kiebotBook.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private List<ErrorDetail> details;
    private String path;

    public ErrorResponse(Instant timestamp, int status, String error, String message, List<ErrorDetail> details, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
        this.path = path;
    }
}

