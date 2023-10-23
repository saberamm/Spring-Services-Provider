package com.example.servicesprovider.exception;

public class DuplicateSubServiceNameException extends RuntimeException {
    public DuplicateSubServiceNameException(String message) {
        super(message);
    }

    public DuplicateSubServiceNameException() {
    }
}