package com.example.serverpost.exception;

public class PostException extends RuntimeException {
    private final Long postID;

    public PostException(Long postID){
        this.postID = postID;
    }

    @Override
    public String getMessage() {
        return "for {postId = " +  this.postID  + "} publication not found";
    }
}
