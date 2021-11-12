package com.example.serverpost.exception.post;


public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }
}
