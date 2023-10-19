package com.example.servicesprovider.exception;

public class OrderNotStartedYetException extends Exception {
    public OrderNotStartedYetException() {
    }

    public OrderNotStartedYetException(String message) {
        super(message);
    }
}
