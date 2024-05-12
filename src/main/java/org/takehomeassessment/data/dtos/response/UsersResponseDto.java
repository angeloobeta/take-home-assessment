package org.takehomeassessment.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersResponseDto {

    private Long id;
    private String phoneNumber;
    private String fullName;
    private boolean isVerified;
}
