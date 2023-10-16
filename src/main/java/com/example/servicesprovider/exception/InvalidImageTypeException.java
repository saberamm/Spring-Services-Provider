package com.example.servicesprovider.exception;

import java.io.IOException;

public class InvalidImageTypeException extends IOException {
    public InvalidImageTypeException(String message) {
        super(message);
    }

    public InvalidImageTypeException() {
    }
}