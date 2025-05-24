package com.example.kiebotBook.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final String message;
    private final List<ErrorDetail> details;
    private final HttpStatus status;

    public BadRequestException(String message, String field, String detailMessage) {
        super(message);
        this.message = message;
        this.details = List.of(new ErrorDetail(field, detailMessage));
        this.status = HttpStatus.BAD_REQUEST;
    }}
