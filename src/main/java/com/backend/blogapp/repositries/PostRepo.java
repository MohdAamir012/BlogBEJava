package com.backend.blogapp.repositries;

import com.backend.blogapp.entities.Post;
import com.backend.blogapp.entities.User;
import com.backend.blogapp.payloads.PostDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepo extends MongoRepository<Post, ObjectId> {
    List<Post> findByTitleContaining(String postTitle);
}
