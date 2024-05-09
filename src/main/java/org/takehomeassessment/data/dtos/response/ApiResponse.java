package org.takehomeassessment.data.dtos.response;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ApiResponse {

    private Object data;
    private boolean isSuccessful;
}
