package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.entities.User;
import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.repositries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {

       User user = userRepo.findByUserName(username);
       if (user!=null){
           UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                   .username(user.getUserName())
                   .password(user.getPassword())
                   .roles(user.getRoles().toArray(new String[0]))
                   .build();
           return userDetails;
       }
    throw new ResourceNotFoundException("User ","userName ",username);
    }
}
