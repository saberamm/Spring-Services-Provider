package com.example.servicesprovider.exception;

public class OfferTimeBeforeOrderTimeException extends Exception{
    public OfferTimeBeforeOrderTimeException() {
    }

    public OfferTimeBeforeOrderTimeException(String message) {
        super(message);
    }
}
