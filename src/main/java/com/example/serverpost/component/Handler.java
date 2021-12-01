package com.example.serverpost.component;

import com.example.serverpost.exception.category.CategoryException;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.exception.post.PostValidationException;
import com.example.serverpost.exception.user.UserNotFoundException;
import com.example.serverpost.exception.user.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = PostNotFoundException.class)
    protected ResponseEntity<Object> get(PostNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryException.class)
    protected ResponseEntity<Object> get(CategoryException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<Object> get(UserNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PostPriceException.class)
    protected ResponseEntity<Object> set(PostPriceException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserValidationException.class)
    protected ResponseEntity<Object> set(UserValidationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostValidationException.class)
    protected ResponseEntity<Object> set(PostValidationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // TODO: 30.11.2021 add @ExceptionHandeler to UserImageException, PostImageException
}
