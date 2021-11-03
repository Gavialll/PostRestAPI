package com.example.serverpost.exception;

public class UserException extends RuntimeException{
    private final Long userID;

    public UserException(Long userID){
        this.userID = userID;
    }

    @Override
    public String getMessage() {
        return "User with { userID = "  + userID + "} not found";
    }
}
