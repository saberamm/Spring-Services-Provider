package com.example.servicesprovider.exception;

public class OrderNotStartedYetException extends RuntimeException {
    public OrderNotStartedYetException() {
    }

    public OrderNotStartedYetException(String message) {
        super(message);
    }
}
