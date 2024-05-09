package org.takehomeassessment.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.takehomeassessment.data.dtos.response.FileResponseDto;
import org.takehomeassessment.data.dtos.response.UsersResponseDto;
import org.takehomeassessment.data.entities.File;
import org.takehomeassessment.data.entities.User;
import org.takehomeassessment.data.repositories.FileRepository;
import org.takehomeassessment.data.repositories.UserRepository;
import org.takehomeassessment.services.cloud.CloudService;
import org.takehomeassessment.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.takehomeassessment.utils.FileUtils.saveFileToDisk;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final CloudService cloudService;
    private final FileRepository fileRepository;

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

    public ApiResponseDto<List<FileResponseDto>> uploadFiles(MultipartFile[] UploadedFiles) throws IOException {
        List<FileResponseDto> fileResponseDtoList = new ArrayList<>();
        String savedVideoFilePath = null;

        // Save video to disk
        for(MultipartFile multipartFile : UploadedFiles){
            System.out.println("Uploading : "+ multipartFile.getOriginalFilename() + "to the server ");
            try {
                savedVideoFilePath = saveFileToDisk(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An Error occurred while writing video file to disk");
            }

            // Generate a unique filename for the video
            assert savedVideoFilePath != null;
            String uploadedFileUrl = StringUtils.cleanPath(savedVideoFilePath);

            // Save the file metadata to the database
            File videoData = File.builder()
                    .fileSize(String.valueOf(multipartFile.getSize()))
                    .fileName(multipartFile.getOriginalFilename())
                    .timeStamp(LocalDateTime.now())
                    .fileUrl(uploadedFileUrl)
                    .build();
            File fileUploaded = fileRepository.save(videoData);
            FileResponseDto fileResponseDto = FileResponseDto.builder()
                    .fileUrl(fileUploaded.getFileUrl())
                    .fileId(String.valueOf(fileUploaded.getFileId()))
                    .timeStamp(fileUploaded.getTimeStamp())
                    .fileName(fileUploaded.getFileName())
                    .fileSize(fileUploaded.getFileSize())
                    .build();

            fileResponseDtoList.add(fileResponseDto);
        }


        return new ApiResponseDto<>("Upload successfully", 200,fileResponseDtoList);
    }

    @Override
    public ApiResponseDto<Optional<File>> findVideoById(String fileId) {

        if(fileRepository.findVideoById(fileId).isPresent())
        {
            return new ApiResponseDto<>("Success", 200, fileRepository.findVideoById(fileId));
        }else {
            return new ApiResponseDto<>("Video doesn't exist", 200, null);
        }
//        VideoResponseDto videoResponse = VideoResponseDto.builder()
//                .fileSize(response.getFileSize())
//                .downloadUrl(response.getFileUrl())
//                .fileId(response.getId())
//                .fileName(response.getFilename())
//                .timeStamp(response.getTimestamp())
//                .build();
    }

    @Override
    public ApiResponseDto<?> getAllUser() {
        return null;
    }
}
