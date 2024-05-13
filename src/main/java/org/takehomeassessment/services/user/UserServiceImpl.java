package org.takehomeassessment.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.takehomeassessment.data.dtos.payload.OtpRequestDto;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.data.dtos.response.ApiResponseDto;
import org.takehomeassessment.data.dtos.response.FileResponseDto;
import org.takehomeassessment.data.dtos.response.OtpResponseDto;
import org.takehomeassessment.data.dtos.response.UsersResponseDto;
import org.takehomeassessment.data.entities.File;
import org.takehomeassessment.data.entities.Otp;
import org.takehomeassessment.data.entities.User;
import org.takehomeassessment.data.repositories.FileRepository;
import org.takehomeassessment.data.repositories.OtpRepository;
import org.takehomeassessment.data.repositories.UserRepository;
import org.takehomeassessment.utils.TwilioUtils;
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
    private final OtpRepository otpRepository;
    private final UserUtils userUtils;
    private final FileRepository fileRepository;
    private final TwilioUtils twilioUtils;

    @Override
    public ApiResponseDto<?> createUser(UserSignupDto signUpRequest) {
        // check if user has already signed up
        boolean isExists = checkIfUserAlreadyExists(signUpRequest.getPhoneNumber());
        if (isExists ){
            User userDetail = new User();
            boolean isVerified = isUserVerified(userDetail.isVerified());
            if(isVerified){
                return new ApiResponseDto<>("User with this phone number is verified and already exists", HttpStatus.BAD_REQUEST.value(),null);
            }
            return new ApiResponseDto<>("User already exists but has been verified", HttpStatus.BAD_REQUEST.value(),null);
        }
        // create new user
        String sentOtpCode = twilioUtils.generateVerificationCode();
        // set the otp payload

        Otp otp = new Otp();
        otp.setPhoneNumber(signUpRequest.getPhoneNumber());
        otp.setOtpCode(sentOtpCode);
        // persist the otp data
        Otp otpRequestDto  = otpRepository.save(otp);
        OtpRequestDto otpResponseDto = OtpRequestDto.builder()
                .phoneNumber(otpRequestDto.getPhoneNumber())
                .build();
        // send the otp verification code
        twilioUtils.sendVerificationCode(otpResponseDto,sentOtpCode);
        User user = new User();
        user.setOtp(otp);
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        // persist the user data
        userRepository.save(user);

        UsersResponseDto usersResponseDto = UsersResponseDto.builder()
                .isVerified(user.isVerified())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return new ApiResponseDto<>("User successfully created", HttpStatus.CREATED.value(), usersResponseDto);
    }

    private boolean checkIfUserAlreadyExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    private boolean isUserVerified(boolean isVerified){
        return userRepository.findByVerified(isVerified);
    }

    @Override
    public ApiResponseDto<UsersResponseDto> getUserDetails() {
        User user = userUtils.getLoggedInUser();
        if (Objects.isNull(user)){
            return new ApiResponseDto<>( "No user details",HttpStatus.BAD_REQUEST.value(), null);
        }

        UsersResponseDto userResponseDto = UsersResponseDto.builder()
                .phoneNumber(user.getPhoneNumber())
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
        String savedFilePath = null;

        // Save video to disk
        for(MultipartFile multipartFile : UploadedFiles){
            System.out.println("Uploading : "+ multipartFile.getOriginalFilename() + "to the server ");
            try {
                savedFilePath = saveFileToDisk(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An Error occurred while writing video file to disk");
            }

            // Generate a unique filename for the video
            assert savedFilePath != null;
            String uploadedFileUrl = StringUtils.cleanPath(savedFilePath);

            // Save the file metadata to the database
            File fileData = File.builder()
                    .fileSize(String.valueOf(multipartFile.getSize()))
                    .fileName(multipartFile.getOriginalFilename())
                    .timeStamp(LocalDateTime.now())
                    .fileUrl(uploadedFileUrl)
                    .build();
            File fileUploaded = fileRepository.save(fileData);
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
    public ApiResponseDto<Optional<File>> findFileById(String fileId) {

        if(fileRepository.findByFileId(fileId).isPresent())
        {
            return new ApiResponseDto<>("Success", 200, fileRepository.findByFileId(fileId));
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
    public ApiResponseDto<?> getUserByNameOrPhoneNumber(String keyword) {
        User fetchedDetail = userRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContainsIgnoreCase(keyword, keyword, keyword)
            .stream()
            .findFirst()
            .orElse(null);
        assert fetchedDetail != null;
        UserDto userDto = UserDto.builder()
                .phone(fetchedDetail.getPhoneNumber())
                .firstName(fetchedDetail.getFirstName())
                .lastName(fetchedDetail.getLastName())
                .profilePic(fetchedDetail.getProfilePic())
                .build();

        return new ApiResponseDto<>("User fetched successfully", 200, userDto);
    }

    @Override
    public ApiResponseDto<?> getAllUser() {
        return null;
    }

}
