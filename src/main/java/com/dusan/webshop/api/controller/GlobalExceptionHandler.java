package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.controller.exception.EmptyFileException;
import com.dusan.webshop.api.controller.exception.UnsupportedFileFormatException;
import com.dusan.webshop.dto.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleException(EmptyFileException e) {
        return new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleException(UnsupportedFileFormatException e) {
        return new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }
}
