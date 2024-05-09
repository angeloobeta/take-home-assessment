package org.takehomeassessment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WhatsAppCloneServe extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public WhatsAppCloneServe(){
        this("Error Processing Request!");
    }

    public WhatsAppCloneServe(String message){
        super(message);
    }

    public WhatsAppCloneServe(String message, HttpStatus status) {
        this(message);
        this.status = status;
    }

}
