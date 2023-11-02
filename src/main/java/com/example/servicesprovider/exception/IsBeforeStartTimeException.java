package com.example.servicesprovider.exception;

public class IsBeforeStartTimeException extends RuntimeException{
    public IsBeforeStartTimeException() {
    }

    public IsBeforeStartTimeException(String message) {
        super(message);
    }
}
