package com.example.boubyan.exception;

import org.springframework.http.HttpStatus;

public class NotFound extends RuntimeException{


    public NotFound(String message) {
        super(message);
    }
}
