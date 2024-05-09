package org.takehomeassessment.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentials extends BlackListException {
    public InvalidCredentials(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
