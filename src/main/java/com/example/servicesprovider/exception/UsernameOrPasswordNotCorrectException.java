package com.example.servicesprovider.exception;

public class UsernameOrPasswordNotCorrectException extends Exception{
    public UsernameOrPasswordNotCorrectException() {
    }

    public UsernameOrPasswordNotCorrectException(String message) {
        super(message);
    }
}
