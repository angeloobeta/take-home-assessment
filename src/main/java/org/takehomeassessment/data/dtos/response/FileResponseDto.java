package org.takehomeassessment.data.dtos.response;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {
    private String fileName;
    private String fileId;
    private LocalDateTime timeStamp;
    private String fileSize;
    private String fileUrl;
}