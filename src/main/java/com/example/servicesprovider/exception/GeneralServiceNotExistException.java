package com.example.servicesprovider.exception;

public class GeneralServiceNotExistException extends Exception {
    public GeneralServiceNotExistException(String message) {
        super(message);
    }

    public GeneralServiceNotExistException() {
    }
}