package org.takehomeassessment.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    @Operation(summary = "Get the logged in User",
            description = "Returns an ApiResponse Response entity containing the user's details")
    @GetMapping("profile")
    public ResponseEntity<?> getProfile(){
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @Operation(summary = "Update User's details",
            description = "Returns an ApiResponse Response entity containing the updated user's details")
    @PatchMapping("update")
    public ResponseEntity<?> addUserDetail(@Valid @RequestBody UserDto userDto){
        return null;
    }

    @Operation(summary = "Search for users",
            description = "You can search by first name, last name, or both names, or email")
    @GetMapping("all-users")
    public ResponseEntity<?> searchForUsers(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.searchByNameOrEmail(keyword));
    }

    @Operation(summary = "Get all users in the database",
            description = "Returns an ApiResponse Response entity containing a list of the users in the database")
    @GetMapping("all")
    public ResponseEntity<?> getAllUsers(){
    return ResponseEntity.ok(userService.getAllUser());
    }

    @Operation(summary = "Get the logged in User's details",
            description = "Returns an ApiResponse Response entity containing the user's details")
    @GetMapping("user-detail")
    public ResponseEntity<?> getUserDetails(){
        return ResponseEntity.ok(userService.getUserDetails());
    }


    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload file to the server",
            description = "Returns an ApiResponse Response entity containing successful message of the uploaded file with details")
    public ResponseEntity<?> uploadFile(
            @RequestPart("file")
            @RequestParam(value="fileName") MultipartFile[] fileName) {
        if (fileName[0].isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("You must select a file!");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.uploadFiles(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
