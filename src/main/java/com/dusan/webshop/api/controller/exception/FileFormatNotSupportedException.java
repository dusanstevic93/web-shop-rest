package com.dusan.webshop.api.controller.exception;

public class FileFormatNotSupportedException extends RuntimeException {

    public FileFormatNotSupportedException() {

    }

    public FileFormatNotSupportedException(String message) {
        super(message);
    }
}
