package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {

    private LocalDateTime timestamp = LocalDateTime.now();
    private int error;
    private String message;

    public ExceptionResponse(int error, String message) {
        this.error = error;
        this.message = message;
    }
}
