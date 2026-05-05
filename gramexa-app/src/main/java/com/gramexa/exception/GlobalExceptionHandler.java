package com.gramexa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    return new ResponseEntity<>(
            new ErrorResponse(400 , ex.getMessage()), HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

    String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.joining(", "));

    return new ResponseEntity<>(
            new ErrorResponse(400, errorMessage),
            HttpStatus.BAD_REQUEST
    );
  }

  //  Handle ALL OTHER exceptions (fallback)
  @ExceptionHandler(Throwable.class)   // 🔥 FIX HERE
  public ResponseEntity<ErrorResponse> handleGeneralException(Throwable ex) {
    return new ResponseEntity<>(
            new ErrorResponse(500, "Something went wrong"),
            HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
