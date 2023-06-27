package com.blog.app.controller;

import com.blog.app.payloads.FileResponse;
import com.blog.app.payloads.PostDto;
import com.blog.app.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/uploadimage")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam("image") MultipartFile image
    ) {
        String fileName = null;
        try {
            fileName = fileService.upload(path, image);
        } catch (IOException e) {

            return new ResponseEntity<FileResponse>(new FileResponse(null, "Not able to upload the image"), HttpStatus.OK);
        }
        return new ResponseEntity<FileResponse>(new FileResponse(fileName, "Image uploaded Succesfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void showImage(
            @PathVariable("imageName") String imageName, HttpServletResponse response
    ) throws FileNotFoundException {
        InputStream resource = fileService.getContentImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            StreamUtils.copy(resource, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
