package com.backend.blogapp.controllers;

import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.ApiResponse;
import com.backend.blogapp.payloads.PostDto;
import com.backend.blogapp.services.CloudinaryImageService;
import com.backend.blogapp.services.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(
                   @RequestParam("post") String postDtoStr,
                   @RequestParam("image") MultipartFile image,
                   @RequestParam("userId") String userIdStr,
                   @RequestParam("catId") String catIdStr) {
        if (!ObjectId.isValid(userIdStr) || !ObjectId.isValid(catIdStr)) {
            throw new ResourceNotFoundException("Invalid user ID or category ID");
        }
        PostDto postDto = null;
        try {
            postDto = objectMapper.readValue(postDtoStr, PostDto.class);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException("Invalid post json value !");
        }
        if (image == null || image.isEmpty()) {
            throw new ResourceNotFoundException("Image file is required");
        }
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId catId = new ObjectId(catIdStr);

        // Upload the image and get the URL
        Map uploadResult = this.cloudinaryImageService.upload(image);
        String imageUrl = (String) uploadResult.get("secure_url");

        // Set the image URL in the PostDto
        postDto.setImage(imageUrl);

        // Create the post
        PostDto createdPost = this.postService.createPost(postDto, userId, catId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
//
//    @PostMapping("/userId/{userId}/categoryId/{categoryId}")
//    public ResponseEntity<PostDto> createPost(@Valid
//    @RequestBody PostDto postDto,
//    @PathVariable("userId") String userIdStr,
//    @PathVariable String categoryId)
//        {
//            if (!ObjectId.isValid(categoryId)) {
//                throw new ResourceNotFoundException("Invalid Category ID : " + categoryId);
//            }
//            ObjectId catId = new ObjectId(categoryId);
//            if (!ObjectId.isValid(userIdStr)) {
//                throw new ResourceNotFoundException("Invalid user ID : " + userIdStr);
//            }
//            ObjectId userId = new ObjectId(userIdStr);
//
//        PostDto savedPost = this.postService.createPost(postDto,userId,catId);
//        return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
//    }

    @PutMapping("/postId/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable("postId") String postIdStr ) {
        if (!ObjectId.isValid(postIdStr)) {
            throw new ResourceNotFoundException("Invalid Post ID : " + postIdStr);
        }
        ObjectId postId = new ObjectId(postIdStr);

        PostDto savedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(savedPost, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> postDtos = this.postService.getAllPosts();
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
    @GetMapping("/postId/{postId}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable("postId") String postIdStr ) {
        if (!ObjectId.isValid(postIdStr)) {
            throw new ResourceNotFoundException("Invalid Post ID : " + postIdStr);
        }
        ObjectId postId = new ObjectId(postIdStr);

        PostDto postDtos = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @DeleteMapping("/postId/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(
            @PathVariable("postId") String postIdStr ) {
        if (!ObjectId.isValid(postIdStr)) {
            throw new ResourceNotFoundException("Invalid Post ID : " + postIdStr);
        }
        ObjectId postId = new ObjectId(postIdStr);

        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully!",true),HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostByUser(
            @PathVariable("userId") String userIdStr){
        if (!ObjectId.isValid(userIdStr)) {
            throw new ResourceNotFoundException("Invalid user ID : " + userIdStr);
        }
        ObjectId userId = new ObjectId(userIdStr);
        List<PostDto> postDtos = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(
            @PathVariable("categoryId") String categoryIdStr){
        if (!ObjectId.isValid(categoryIdStr)) {
            throw new ResourceNotFoundException("Invalid Category ID : " + categoryIdStr);
        }
        ObjectId categoryId = new ObjectId(categoryIdStr);
        List<PostDto> postDtos = this.postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPost(
            @PathVariable("keywords") String keywords){

        List<PostDto> postDtos = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
}