package com.example.servicesprovider.exception;

public class NoOrdersFoundException extends RuntimeException{
    public NoOrdersFoundException() {
    }

    public NoOrdersFoundException(String message) {
        super(message);
    }
}
