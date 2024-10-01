package com.example.boubyan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class NotFoundStudent{

    @ExceptionHandler(NotFound.class)
    public ResponseEntity NotFoundStudent(WebRequest ws){




        return new ResponseEntity<>("Student Not Found",HttpStatus.NOT_FOUND);
    }




}
