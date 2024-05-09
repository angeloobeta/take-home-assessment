package org.takehomeassessment.services.user;


import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.springframework.web.multipart.MultipartFile;
import org.takehomeassessment.data.dtos.response.FileResponseDto;
import org.takehomeassessment.data.entities.File;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    // TODO: CREATE USER

    ApiResponseDto<?> createUser(UserSignupDto signUpRequest);

    ApiResponseDto<?> searchByNameOrEmail(String keyword);

    String uploadFile(MultipartFile file);
    ApiResponseDto<List<FileResponseDto>> uploadFiles(MultipartFile[] file) throws IOException;

    ApiResponseDto<?> getUserDetails();

    ApiResponseDto<UserDto> getLoggedInUser();

    ApiResponseDto<Optional<File>> findVideoById(String fileId);

    ApiResponseDto<?> getAllUser();

}
