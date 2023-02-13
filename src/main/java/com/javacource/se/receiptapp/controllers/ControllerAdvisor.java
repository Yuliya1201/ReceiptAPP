package com.javacource.se.receiptapp.controllers;

import com.javacource.se.receiptapp.exception.FileProcessingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.javacource.se.receiptapp.FileService.FilesController;

import java.io.FileNotFoundException;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)

public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            ,HttpHeaders headers,HttpStatus status,WebRequest request) {

        Map<String,String>errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public String handleFileNotFoundException(FileNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileProcessingException.class)
    public String handleFileProccesingException(FileProcessingException ex) {
        return ex.getMessage();
    }
}
