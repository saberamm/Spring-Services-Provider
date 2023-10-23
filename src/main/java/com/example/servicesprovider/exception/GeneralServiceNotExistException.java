package com.example.servicesprovider.exception;

public class GeneralServiceNotExistException extends RuntimeException {
    public GeneralServiceNotExistException(String message) {
        super(message);
    }

    public GeneralServiceNotExistException() {
    }
}