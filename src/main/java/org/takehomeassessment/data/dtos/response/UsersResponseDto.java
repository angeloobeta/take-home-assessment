package org.takehomeassessment.data.dtos.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsersResponseDto {

    private Long id;
    private String phoneNumber;
    private String fullName;
    private boolean isVerified;
}
