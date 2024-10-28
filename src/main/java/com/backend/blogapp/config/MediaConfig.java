package com.backend.blogapp.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MediaConfig {

    @Bean
    public Cloudinary getCloudinary(){
        Map config =new HashMap<>();
        config.put("cloud_name","dveqbhxxv");
        config.put("api_key","222861532321563");
        config.put("api_secret","JG_m9D8_fJPrK2w7d5r1tvvijXE");
        config.put("secure",true);

        return new Cloudinary(config);
    }
}
