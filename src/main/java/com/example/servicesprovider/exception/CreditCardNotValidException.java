package com.example.servicesprovider.exception;

public class CreditCardNotValidException extends RuntimeException {
    public CreditCardNotValidException() {
    }

    public CreditCardNotValidException(String message) {
        super(message);
    }
}
