package org.takehomeassessment.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ExceptionResponse {
    private String message;
    private String path;
    private String time;
    private int statusCode;

}
