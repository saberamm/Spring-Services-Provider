package com.example.servicesprovider.exception;

public class NoOrdersFoundException extends Exception{
    public NoOrdersFoundException() {
    }

    public NoOrdersFoundException(String message) {
        super(message);
    }
}
