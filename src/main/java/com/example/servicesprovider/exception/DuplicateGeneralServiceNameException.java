package com.example.servicesprovider.exception;

public class DuplicateGeneralServiceNameException extends RuntimeException {
    public DuplicateGeneralServiceNameException() {
    }

    public DuplicateGeneralServiceNameException(String message) {
        super(message);
    }
}
