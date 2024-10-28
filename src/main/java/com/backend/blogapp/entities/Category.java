package com.backend.blogapp.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Category {
    @Id
    private ObjectId id;
    @NotNull
    private String title;
    @NotNull
    private String description;

    @DBRef
    private List<Post> posts = new ArrayList<>();
}
