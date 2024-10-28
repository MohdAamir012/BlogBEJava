package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.entities.Category;
import com.backend.blogapp.entities.Post;
import com.backend.blogapp.entities.User;
import com.backend.blogapp.exceptions.ResourceNotFoundException;
import com.backend.blogapp.payloads.PostDto;
import com.backend.blogapp.repositries.CategoryRepo;
import com.backend.blogapp.repositries.PostRepo;
import com.backend.blogapp.repositries.UserRepo;
import com.backend.blogapp.services.PostService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    private Post dtoToPost(PostDto postDto) {
        Post post = this.modelMapper.map(postDto, Post.class);
        return post;
    }
    private PostDto postToDto(Post post) {
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return  postDto;
    }

    @Override
    public PostDto createPost(PostDto postDto,ObjectId userId,ObjectId catId) {
        User user =this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","userId ",userId.toString()));
        Category category =this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category ","category Id ",catId.toString()));

        Post post =dtoToPost(postDto);
        post.setDateCreated(LocalDateTime.now());
        Post savedPost = this.postRepo.save(post);

        user.getPosts().add(savedPost);
        User savedUser= this.userRepo.save(user);
        category.getPosts().add(savedPost);
        Category savedCategory= this.categoryRepo.save(category);
        return this.postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, ObjectId postId) {
        Post oldPost = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","postId ",postId.toString()));
        oldPost.setTitle(postDto.getTitle());
        oldPost.setDescription(postDto.getDescription());
        oldPost.setImage(postDto.getImage());
        Post savedPost = this.postRepo.save(oldPost);
        return this.postToDto(savedPost);
    }

    @Override
    public void deletePost(ObjectId postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","Id ",postId.toString()));
        this.postRepo.deleteById(post.getId());
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepo.findAll();
        List <PostDto> postDtos= posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(ObjectId postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ","postId ",postId.toString()));
        return this.postToDto(post);
    }

    @Override
    public List<PostDto> getAllPostByUser(ObjectId userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","userId ",userId.toString()));
        List<Post> posts = user.getPosts();

        if (posts == null || posts.isEmpty()) {
            return new ArrayList<PostDto>();
        }

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.postToDto(post))
                .collect(Collectors.toList());
        return postDtos;
     }
    @Override
    public List<PostDto> getAllPostsByCategory(ObjectId categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ","categoryId ",categoryId.toString()));
        List<Post> posts = category.getPosts();

        if (posts == null || posts.isEmpty()) {
            return new ArrayList<PostDto>();
        }

        List<PostDto> postDtos = posts.stream()
                .map(post -> this.postToDto(post))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keywords) {
        List <Post> posts= this.postRepo.findByTitleContaining(keywords);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
