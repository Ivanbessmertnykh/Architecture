package com.example.moviereviewer.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice //Обработчик исключений. Когда у нас метод выбрасывает ошибку, методы этого контроллера ловят ошибки и выдают вменяемый ответ
public class ErrorHandler {

    @ExceptionHandler({IllegalArgumentException.class, TypeMismatchException.class})
    public ResponseEntity<?> badRequestErrorHandler(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notfoundErrorHandler(Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
}
