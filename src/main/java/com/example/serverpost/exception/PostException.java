package com.example.serverpost.exception;


public class PostException extends RuntimeException {
    private String message = "Публікацю незнайдено";

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
