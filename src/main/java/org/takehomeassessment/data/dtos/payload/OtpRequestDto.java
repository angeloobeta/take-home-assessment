package org.takehomeassessment.data.dtos.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OtpRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;

}