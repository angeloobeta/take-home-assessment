package org.takehomeassessment.exceptions.handler;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.takehomeassessment.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WhatsAppCloneServe.class)
    public ResponseEntity<WhatsAppExceptionResponse> apiRequestExceptionHandler(WhatsAppCloneServe e) {
        log.info(e.getMessage());
        WhatsAppExceptionResponse response =
                WhatsAppExceptionResponse.builder()
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build();
        return new ResponseEntity<>(response, e.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {

        Map<String, String> data = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            data.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        var response =
                WhatsAppExceptionResponse.builder()
                        .data(data)
                        .message("Bad Request")
                        .status(status)
                        .build();

        return new ResponseEntity<>(response, status);
    }
}
