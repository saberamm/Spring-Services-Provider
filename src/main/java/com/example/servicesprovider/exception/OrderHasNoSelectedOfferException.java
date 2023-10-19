package com.example.servicesprovider.exception;

public class OrderHasNoSelectedOfferException extends Exception {
    public OrderHasNoSelectedOfferException() {
    }

    public OrderHasNoSelectedOfferException(String message) {
        super(message);
    }
}
