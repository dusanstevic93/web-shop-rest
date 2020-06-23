package com.dusan.webshop.service.exception;

public class UsernameIsTakenException extends RuntimeException {

    public UsernameIsTakenException() {

    }

    public UsernameIsTakenException(String message) {
        super(message);
    }
}
