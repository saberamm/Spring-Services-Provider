package com.example.servicesprovider.exception;

public class UsernameOrPasswordNotCorrectException extends RuntimeException{
    public UsernameOrPasswordNotCorrectException() {
    }

    public UsernameOrPasswordNotCorrectException(String message) {
        super(message);
    }
}
