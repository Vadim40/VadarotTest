package com.example.vadarodtest.Handling;

import com.example.vadarodtest.Exceptions.CurrencyRateNotFoundException;
import com.example.vadarodtest.Models.DTOs.ResponseValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CurrencyRateNotFoundException.class)
    public ResponseEntity<String> handleCurrencyRateNotFoundException(CurrencyRateNotFoundException e) {
        log.warn("CurrencyRateNotFoundException: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidationError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseValidationError errorResponse = new ResponseValidationError(ex.getBindingResult());
        log.warn("Validation errors: {}", errorResponse.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

