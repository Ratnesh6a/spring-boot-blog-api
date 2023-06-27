package com.blog.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public interface FileService {
    // Upload Image program
    String  upload (String path, MultipartFile file) throws IOException;
    // to get the image
    InputStream getContentImage(String path,String fileName) throws FileNotFoundException;


}
