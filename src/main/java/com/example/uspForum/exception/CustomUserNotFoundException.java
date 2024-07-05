package com.example.uspForum.exception;

public class CustomUserNotFoundException extends RuntimeException {
    public CustomUserNotFoundException(String message) {
        super(message);
    }
}
