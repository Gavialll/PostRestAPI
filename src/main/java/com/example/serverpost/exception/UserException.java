package com.example.serverpost.exception;

public class UserException extends Exception{
    private String message = "Користувача незнайдено";

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
