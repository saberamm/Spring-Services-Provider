package com.example.servicesprovider.exception;

public class PasswordsNotEqualException extends Exception {
    public PasswordsNotEqualException() {
    }

    public PasswordsNotEqualException(String message) {
        super(message);
    }
}
