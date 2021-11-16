package com.example.serverpost.exception.user;

public class UserImageException extends RuntimeException{

    public UserImageException(String message) {
        super(message);
    }

    public UserImageException(Throwable cause) {
        super(cause);
    }
}
