package com.dusan.webshop.service.exception;

public class EmailIsTakenException extends RuntimeException {

    public EmailIsTakenException() {

    }

    public  EmailIsTakenException(String exception) {
        super(exception);
    }
}
