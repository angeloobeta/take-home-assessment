package org.takehomeassessment.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.data.dtos.payload.UserSignupDto;
import org.takehomeassessment.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create Account",
    // write a description for the endpoint
    // write a description for the endpoint
    description = "Returns an ApiResponse Response entity containing the user's details after creating an account")
    @PostMapping("create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody UserSignupDto userSignupDto){
        return ResponseEntity.ok(userService.createUser(userSignupDto));
    }

    @Operation(summary = "Login",
    description = "Returns an ApiResponse Response entity containing the user's details after logging in")
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.login(userDto));
    }

    @Operation(summary = "Logout",
    description = "Returns an ApiResponse Response entity containing the user's details after logging out")
    @PostMapping("logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok(userService.logout());
    }

    @Operation(summary = "Get User by Id",
    description = "Returns an ApiResponse Response entity containing the user's details")
    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @Operation(summary = "Get User by Email",
    description = "Returns an ApiResponse Response entity containing the user's details")
    @GetMapping("email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    //  get user by phone number
    @Operation(summary = "Get User by Phone Number",    
    description = "Returns an ApiResponse Response entity containing the user's details")
    @GetMapping("phone/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber){
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    



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
            description = "You can search by first name, last name, or both names, or phoneNumber")
    @GetMapping("all-users")
    public ResponseEntity<?> searchForUsers(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.getUserByNameOrPhoneNumber(keyword));
    }

    @Operation(summary = "Search for file by Id",
            description = "You can search for file by id")
    @GetMapping("all-users")
    public ResponseEntity<?> searchForFileById(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.findFileById(keyword));
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
