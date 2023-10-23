package com.example.servicesprovider.exception;

public class PriceIsLowerThanBasePriceException extends RuntimeException{
    public PriceIsLowerThanBasePriceException() {
    }

    public PriceIsLowerThanBasePriceException(String message) {
        super(message);
    }
}