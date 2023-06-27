package com.blog.app.service.impl;

import com.blog.app.exception.ImageNotFoundException;
import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static java.io.File.*;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(String path, MultipartFile file) throws IOException {
        // File name
        String name=file.getOriginalFilename();
        validateFileType(name);
        System.out.println("Here is the name ->"+name);

        //Full path

       // Code to generate random Name
        String randomId= UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf('.')));
// Not Used above one
        String filePath = path+ separator+name;

        File f = new File(path);
            if(f.exists()){
                f.mkdir();
        }
            //Files .copy takes target and source
        Files.copy(file.getInputStream(), Paths.get(filePath));
            return name;
    }

    @Override
    public InputStream getContentImage(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+ separator+fileName;
        InputStream is = new FileInputStream(fullPath);

        // Db logic to return input Stream
        return is;
    }


    public static void validateFileType(String fileName) {
        if (!fileName.toLowerCase().endsWith(".jpg")) {
         throw new ImageNotFoundException();
            // Rest of your code here
        }
    }
}
