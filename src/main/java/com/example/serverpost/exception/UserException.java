package com.example.serverpost.exception;

public class UserException extends Exception{

    @Override
    public String getMessage() {
        return "Користувача незнайдено";
    }
}
