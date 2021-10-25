package com.example.serverpost.exception;

public class PostException extends Exception {
    private String message = "Публікацю незнайдено";

    public PostException(String message) {
        this.message = message;
    }
    public PostException() {
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
