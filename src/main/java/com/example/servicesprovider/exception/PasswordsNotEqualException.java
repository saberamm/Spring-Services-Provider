package com.example.servicesprovider.exception;

public class PasswordsNotEqualException extends RuntimeException {
    public PasswordsNotEqualException() {
    }

    public PasswordsNotEqualException(String message) {
        super(message);
    }
}
