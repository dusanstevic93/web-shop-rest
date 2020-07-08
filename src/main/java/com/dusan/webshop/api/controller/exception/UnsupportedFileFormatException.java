package com.dusan.webshop.api.controller.exception;

public class UnsupportedFileFormatException extends RuntimeException {

    public UnsupportedFileFormatException() {

    }

    public UnsupportedFileFormatException(String message) {
        super(message);
    }
}
