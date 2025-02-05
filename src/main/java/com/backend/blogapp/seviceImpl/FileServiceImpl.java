package com.backend.blogapp.seviceImpl;

import com.backend.blogapp.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

//		File Name like abc.png
        String name = file.getOriginalFilename();

//		random name genrate
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf('.')));

//		Full Path
        String filePath = path + File.separator + fileName1;

//		create folder
        File f= new File(path);
        if(!f.exists())f.mkdir();

//		file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);

        return is;
    }

}
