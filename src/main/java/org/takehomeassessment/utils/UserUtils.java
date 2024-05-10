package org.takehomeassessment.utils;

import lombok.RequiredArgsConstructor;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.entities.User;
import org.takehomeassessment.data.repositories.UserRepository;
import org.takehomeassessment.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    public User getLoggedInUser(){

        return userRepository.findByEmail("To be filled")
                .orElseThrow(()-> new UserNotFoundException("User with email does not exists"));
    }

    public UserDto mapUserToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .phone(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePic(user.getProfilePic())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
