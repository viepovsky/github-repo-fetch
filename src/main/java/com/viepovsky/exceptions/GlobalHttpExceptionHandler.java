package com.viepovsky.exceptions;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpClientErrorException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(ex.getStatusCode().value());
        response.setMessage(ex.getStatusCode().toString());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @Data
    public static class ErrorResponse {
        private int status;
        private String message;
    }
}

