package com.example.servicesprovider.exception;

public class TechnicianNotConfirmedYetException extends RuntimeException{
    public TechnicianNotConfirmedYetException() {
    }

    public TechnicianNotConfirmedYetException(String message) {
        super(message);
    }
}