package com.backend.blogapp.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NotNull
    private String userName;
    @NotNull
    private String password;
    private String about;
    private LocalDateTime dateCreated;
    private List<String> roles;
    @DBRef
    private List<Post>posts = new ArrayList<>();
}
