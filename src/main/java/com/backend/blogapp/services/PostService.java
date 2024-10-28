package com.backend.blogapp.services;

import com.backend.blogapp.payloads.PostDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    //	create Post
    PostDto createPost(PostDto postDto,ObjectId userId,ObjectId catId);

    //	update Post
    PostDto updatePost(PostDto postDto,ObjectId postId);

    //	delete post
    void deletePost(ObjectId postId);

    //	getAll Posts
    List<PostDto> getAllPosts();

    //	getPostByID
    PostDto getPostById(ObjectId postId);

//	get post of category

    List<PostDto> getAllPostsByCategory(ObjectId categoryId);

//	get post by user

    List<PostDto> getAllPostByUser(ObjectId userId);

//	get searched Post

    List <PostDto> searchPosts(String keywords);

}
