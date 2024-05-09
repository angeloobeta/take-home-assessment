package org.takehomeassessment.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BlackListException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
