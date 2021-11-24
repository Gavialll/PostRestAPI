package com.example.serverpost.component;

import com.example.serverpost.exception.RegistrationException;
import com.example.serverpost.exception.category.CategoryException;
import com.example.serverpost.exception.post.PostAddException;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.exception.user.UserNotFoundException;
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

    @ExceptionHandler(value = PostAddException.class)
    protected ResponseEntity<Object> add(PostAddException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PostPriceException.class)
    protected ResponseEntity<Object> price(PostPriceException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RegistrationException.class)
    protected ResponseEntity<Object> price(RegistrationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
