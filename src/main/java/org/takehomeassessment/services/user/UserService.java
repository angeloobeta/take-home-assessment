package org.takehomeassessment.services.user;


import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.springframework.web.multipart.MultipartFile;
import org.takehomeassessment.data.dtos.response.FileResponseDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    // TODO: CREATE USER

    ApiResponseDto<?> createUser(UserSignupDto signUpRequest);

    ApiResponseDto<List<FileResponseDto>> uploadFiles(MultipartFile[] file) throws IOException;

    ApiResponseDto<?> getUserDetails();

    ApiResponseDto<UserDto> getLoggedInUser();

    ApiResponseDto<?> findFileById(String fileId);
    ApiResponseDto<?> getUserByNameOrPhoneNumber(String query);

    ApiResponseDto<?> getAllUser();
}
