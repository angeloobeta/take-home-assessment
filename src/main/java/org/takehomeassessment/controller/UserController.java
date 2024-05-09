package org.takehomeassessment.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.takehomeassessment.data.dtos.payload.UserDto;
import org.takehomeassessment.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @Operation(summary = "Update A Particular User's profile picture",
            description = "Returns an ApiResponse Response entity containing the operation details")
    @PatchMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfilePicture(@RequestParam(value = "file") MultipartFile file) {
        try {
            String response = userService.uploadProfileImage(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
