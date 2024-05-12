package org.takehomeassessment.data.dtos.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}