package com.example.serverpost.exception.post;


public class PostAddException extends RuntimeException {
    public PostAddException(String message) {
        super(message);
    }

    public PostAddException(Throwable cause) {
        super(cause);
    }
}
