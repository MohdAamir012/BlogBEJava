package com.backend.blogapp.payloads;

import com.backend.blogapp.entities.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {
    private String id;
    private String title;
    private String description;
    private List<Post> posts = new ArrayList<>();
}
