package com.example.servicesprovider.exception;

public class TechnicianNotConfirmedYetException extends Exception{
    public TechnicianNotConfirmedYetException() {
    }

    public TechnicianNotConfirmedYetException(String message) {
        super(message);
    }
}