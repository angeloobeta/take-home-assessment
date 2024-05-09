package org.takehomeassessment.data.dtos.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String refreshToken;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private String profilePic;

}
