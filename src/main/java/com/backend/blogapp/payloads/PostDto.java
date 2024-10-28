package com.backend.blogapp.payloads;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    private String id;
    private String title;
    private String description;
    private String image;
    private LocalDateTime dateCreated;

}
