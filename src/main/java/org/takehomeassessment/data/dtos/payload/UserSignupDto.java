package org.takehomeassessment.data.dtos.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDto {
    @NotBlank(message = "phone number is required")
    private String phoneNumber;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "first is required")
    private String firstName;
    @NotBlank(message = "lastname is required")
    private String lastName;
}
