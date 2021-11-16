package com.example.serverpost.exception;

public class LoginDuplicateException extends RuntimeException {
    public LoginDuplicateException(String message) {
        super(message);
    }
}
