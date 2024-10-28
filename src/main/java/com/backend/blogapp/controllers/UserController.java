package com.backend.blogapp.controllers;

import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.ApiResponse;
import com.backend.blogapp.payloads.UserDto;
import com.backend.blogapp.services.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createUserDto,HttpStatus.CREATED);

    }
    @PutMapping("userId/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") String userIdStr){
        if (!ObjectId.isValid(userIdStr)) {
            throw new ResourceNotFoundException("Invalid userID : " + userIdStr);
        }
        ObjectId userId = new ObjectId(userIdStr);
        UserDto updatedUserDto = this.userService.updateUser(userDto,userId);
        return new ResponseEntity<UserDto>(updatedUserDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtos= this.userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
    }

@GetMapping("/userId/{userId}")
public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userIdStr){
    if (!ObjectId.isValid(userIdStr)) {
        throw new ResourceNotFoundException("Invalid userID : " + userIdStr);
    }
    ObjectId userId = new ObjectId(userIdStr);
    return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
}
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") String userIdStr){
        if (!ObjectId.isValid(userIdStr)) {
            throw new ResourceNotFoundException("Invalid userID : " + userIdStr);
        }
        ObjectId userId = new ObjectId(userIdStr);
        this.userService.deleteUser(userId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully!",true),HttpStatus.OK);
    }
}
