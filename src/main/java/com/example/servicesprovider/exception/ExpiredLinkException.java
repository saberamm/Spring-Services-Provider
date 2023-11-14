package com.example.servicesprovider.exception;

public class ExpiredLinkException extends RuntimeException {
    public ExpiredLinkException() {
    }

    public ExpiredLinkException(String message) {
        super(message);
    }
}
