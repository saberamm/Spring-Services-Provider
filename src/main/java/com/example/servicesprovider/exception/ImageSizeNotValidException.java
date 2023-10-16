package com.example.servicesprovider.exception;

import java.io.IOException;

public class ImageSizeNotValidException extends IOException {
    public ImageSizeNotValidException(String message) {
        super(message);
    }

    public ImageSizeNotValidException() {
    }
}