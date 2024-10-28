package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.services.CloudinaryImageService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CoudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    public Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {
       try {
           Map data = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
           return data;
       }catch(IOException e){
           throw new RuntimeException("Image upload failed !");
       }
    }
}
