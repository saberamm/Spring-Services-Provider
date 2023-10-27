package com.example.servicesprovider.exception;

public class NotEnoughCreditException extends RuntimeException {
    public NotEnoughCreditException() {
    }

    public NotEnoughCreditException(String message) {
        super(message);
    }
}
