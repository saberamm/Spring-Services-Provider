package com.example.servicesprovider.exception;

public class OfferTimeBeforeOrderTimeException extends RuntimeException{
    public OfferTimeBeforeOrderTimeException() {
    }

    public OfferTimeBeforeOrderTimeException(String message) {
        super(message);
    }
}
