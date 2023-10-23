package com.example.servicesprovider.exception;

public class OrderHasNoSelectedOfferException extends RuntimeException {
    public OrderHasNoSelectedOfferException() {
    }

    public OrderHasNoSelectedOfferException(String message) {
        super(message);
    }
}
