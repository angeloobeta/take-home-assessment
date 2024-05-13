package org.takehomeassessment.data.dtos.response;

import lombok.*;
import org.takehomeassessment.data.entities.OtpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OtpResponseDto {
    private OtpStatus status;
    private String message;
}