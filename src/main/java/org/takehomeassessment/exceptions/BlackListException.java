package org.takehomeassessment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlackListException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public BlackListException(){
        this("Error Processing Request!");
    }

    public BlackListException(String message){
        super(message);
    }

    public BlackListException(String message, HttpStatus status) {
        this(message);
        this.status = status;
    }

}
