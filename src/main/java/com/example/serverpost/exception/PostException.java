package com.example.serverpost.exception;

public class PostException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Публікацю незнайдено";
    }
}
