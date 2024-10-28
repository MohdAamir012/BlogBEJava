package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.entities.User;
import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.UserDto;
import com.backend.blogapp.repositries.UserRepo;
import com.backend.blogapp.services.UserService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }
    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            throw new ResourceNotFoundException("User name cannot be null or empty");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new ResourceNotFoundException("Password cannot be null or empty");
        }
        User user =dtoToUser(userDto);
        user.setDateCreated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser= this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto newUserDto, ObjectId userId) {

        if (newUserDto.getUserName() == null || newUserDto.getUserName().isEmpty()) {
            throw new ResourceNotFoundException("User name cannot be null or empty");
        }
        if (newUserDto.getPassword() == null || newUserDto.getPassword().isEmpty()) {
            throw new ResourceNotFoundException("Password cannot be null or empty");
        }

        User oldUser=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id ",userId.toString()));
        oldUser.setUserName(newUserDto.getUserName());
        oldUser.setAbout(newUserDto.getAbout());
        oldUser.setPassword(newUserDto.getPassword());

        User updatedUser= this.userRepo.save(oldUser);
        return this.userToDto(updatedUser);
    }

    public UserDto getUserById(ObjectId userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id ",userId.toString()));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List <UserDto> userDtos= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(ObjectId userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id ",userId.toString()));
        this.userRepo.deleteById(user.getId());
    }
}
