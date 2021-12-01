package com.example.serverpost.exception.post;

public class PostImageException extends RuntimeException {
    public PostImageException(String message) {
        super(message);
    }

    public PostImageException(Throwable cause) {
        super(cause);
    }
}
