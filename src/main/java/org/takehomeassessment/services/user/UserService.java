package org.takehomeassessment.services.user;


import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    // TODO: CREATE USER

    ApiResponseDto<?> createUser(UserSignupDto signUpRequest);

    ApiResponseDto<?> searchByNameOrEmail(String keyword);

    String uploadFile(MultipartFile file);

    ApiResponseDto<?> getUserDetails();

    ApiResponseDto<UserDto> getLoggedInUser();

    ApiResponseDto<?> getAllUser();

}
