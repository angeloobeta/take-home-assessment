package org.takehomeassessment.services.file;

import org.takehomeassessment.data.dtos.response.ApiResponseDto;

public interface FileService {
    ApiResponseDto<?> findFileByName(String fileName);
    ApiResponseDto<?> findFileByFileSize(String fileSize);
    ApiResponseDto<?> findByFileIdOrFileName(String id);
    ApiResponseDto<?> findByFileId(String id);


}
