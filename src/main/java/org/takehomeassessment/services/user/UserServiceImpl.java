package org.takehomeassessment.services.user;

import lombok.RequiredArgsConstructor;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.takehomeassessment.data.dtos.response.UsersResponseDto;
import org.takehomeassessment.data.entities.User;
import org.takehomeassessment.data.repositories.UserRepository;
import org.takehomeassessment.services.cloud.CloudService;
import org.takehomeassessment.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final CloudService cloudService;

    @Override
    public ApiResponseDto<?> createUser(UserSignupDto signUpRequest) {
        // check if user has already signed up
        boolean isExists = checkIfUserAlreadyExists(signUpRequest.getEmail());
        if (isExists){
            return new ApiResponseDto<>("User already exists", HttpStatus.BAD_REQUEST.value(),null);
        }
        // create new user
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        return new ApiResponseDto<>();
    }

    private boolean checkIfUserAlreadyExists(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public ApiResponseDto<?> searchByNameOrEmail(String keyword){
        return new ApiResponseDto<>("Users Fetched Successfully", 200, null);
    }

    @Override
    public String uploadFile(MultipartFile fileUpload) {
        User foundUser = userUtils.getLoggedInUser();
        String fileUrl = cloudService.upload(fileUpload);
        foundUser.setProfilePic(fileUrl);
        userRepository.save(foundUser);
        return fileUrl;
    }

    @Override
    public ApiResponseDto<UsersResponseDto> getUserDetails() {
        User user = userUtils.getLoggedInUser();
        if (Objects.isNull(user)){
            return new ApiResponseDto<>( "No user details",HttpStatus.BAD_REQUEST.value(), null);
        }

        UsersResponseDto userResponseDto = UsersResponseDto.builder()
                .email(user.getEmail())
                .fullName(user.getFirstName() + user.getLastName())
                .build();

        return new ApiResponseDto<>("successfully got user details",HttpStatus.OK.value(),userResponseDto);
    }

    @Override
    public ApiResponseDto<UserDto> getLoggedInUser(){
        User user = userUtils.getLoggedInUser();
        return new ApiResponseDto<>("Request Successful", 200,userUtils.mapUserToDto(user));
    }

    @Override
    public ApiResponseDto<?> getAllUser() {
        return null;
    }
}
