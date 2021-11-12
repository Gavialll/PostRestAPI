package com.example.serverpost.exception.post;

public class PostPriceException extends RuntimeException {
    public PostPriceException(String message) {
        super(message);
    }

    public PostPriceException(Throwable cause) {
        super(cause);
    }
}
