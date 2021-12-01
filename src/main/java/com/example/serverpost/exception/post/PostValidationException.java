package com.example.serverpost.exception.post;

public class PostValidationException extends RuntimeException{
    public PostValidationException(String message) {
        super(message);
    }

    public PostValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
