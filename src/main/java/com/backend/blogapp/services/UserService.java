package com.backend.blogapp.services;

import com.backend.blogapp.payloads.UserDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,ObjectId userId);
    UserDto getUserById(ObjectId userId);
    List<UserDto> getAllUsers();
    void deleteUser(ObjectId userId);
}
