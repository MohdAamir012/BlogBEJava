package com.backend.blogapp.payloads;

import com.backend.blogapp.entities.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private String id;
    @Indexed(unique = true)
    @NotNull
    private String userName;
    @NotNull
    private String password;
    private String about;
    private LocalDateTime dateCreated;
    private List<String> roles;
    private List<Post>posts = new ArrayList<>();
}
