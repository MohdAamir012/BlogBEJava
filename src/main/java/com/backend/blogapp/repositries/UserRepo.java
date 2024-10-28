package com.backend.blogapp.repositries;

import com.backend.blogapp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository <User, ObjectId>{
    User findByUserName(String username);
}
