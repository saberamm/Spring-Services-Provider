package com.example.servicesprovider.exception;

public class PriceIsLowerThanBasePriceException extends Exception{
    public PriceIsLowerThanBasePriceException() {
    }

    public PriceIsLowerThanBasePriceException(String message) {
        super(message);
    }
}