package com.backend.blogapp.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Post {
    @Id
    private ObjectId id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String image;
    private LocalDateTime dateCreated;

}
