package com.backend.blogapp.repositries;

import com.backend.blogapp.entities.Category;
import com.backend.blogapp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepo extends MongoRepository<Category, ObjectId> {
}
