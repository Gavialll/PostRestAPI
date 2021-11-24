package com.example.serverpost.exception;

public class PostImageException extends RuntimeException {
    public PostImageException(String message) {
        super(message);
    }

    public PostImageException(Throwable cause) {
        super(cause);
    }
}
