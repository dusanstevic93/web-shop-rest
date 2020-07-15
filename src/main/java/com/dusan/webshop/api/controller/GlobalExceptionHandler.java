package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.controller.exception.EmptyFileException;
import com.dusan.webshop.api.controller.exception.UnsupportedFileFormatException;
import com.dusan.webshop.dto.response.ExceptionResponse;
import com.dusan.webshop.service.exception.ConflictException;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(HttpMessageNotReadableException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "JSON parse error");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(MethodArgumentNotValidException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Required field is missing or invalid");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleException(BadCredentialsException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN.value(), "Bad credentials");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleException(ResourceNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleException(ConflictException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }
}
