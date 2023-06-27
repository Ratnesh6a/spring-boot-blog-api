package com.blog.app.controller;

import com.blog.app.entity.Post;
import com.blog.app.payloads.PostDto;
import com.blog.app.request.PaginationRequest;
import com.blog.app.response.ApiResponse;
import com.blog.app.response.PostResponses;
import com.blog.app.service.FileService;
import com.blog.app.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private FileService fileService;

    @PostMapping("/userId/{userId}/categoryId/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Long categoryId )
    {
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto> >getPostByUserId(@Valid @PathVariable Integer userId) {
        List<PostDto> postDtos = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto> >getPostByCategoryId(@Valid @PathVariable Long categoryId) {
        List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

//     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getallPost")
    public  ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> allPostDetails = this.postService.getAllPost();
        return new ResponseEntity<>(allPostDetails,HttpStatus.OK);

    }

    @PostMapping("/getallPostByPage")
    public ResponseEntity<PostResponses> getAllPostPage(@RequestBody PaginationRequest paginationRequest) {
        Integer pageNumber = paginationRequest.getPageNumber();
        Integer pageSize = paginationRequest.getPageSize();
        PostResponses allPostDetails = this.postService.getAllPostPerPage(pageNumber, pageSize);
        return new ResponseEntity<>(allPostDetails, HttpStatus.OK);
    }

    @GetMapping ("/sortByPostid")
    public ResponseEntity<PostResponses> getAllPostPageAndSort(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "0",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "0",required = false) String sortBy
    ) {

        PostResponses allPostDetails = this.postService.getAllPostPageAndSort(pageNumber, pageSize,sortBy);
        return new ResponseEntity<>(allPostDetails, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@Valid @PathVariable Integer postId){
        PostDto postById = postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
    }

    @GetMapping("/deleteById/{postID}")
    public ResponseEntity<?>deletePostById(@Valid @PathVariable Integer postID){
        postService.deletePost(postID);
        return new ResponseEntity<>(new ApiResponse("Post Deleted" , true),HttpStatus.FOUND);

    }

    /*@PostMapping("/updatedPost")
    public ResponseEntity<PostDto> updatePostById(@RequestBody Map<String,Integer> request){
        Integer postId = request.get();
        postService.updatePost()
    }*/
    @PutMapping("/updateByPost/{postId}")
    public ResponseEntity<?>updatePostById(@Valid @RequestBody PostDto postDto ,@PathVariable Integer postId){
        postService.updatePost(postDto,postId);
        return new ResponseEntity<>(new ApiResponse("Post Updated" , true),HttpStatus.FOUND);

    }
    //Search
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword){
        List<PostDto> postDtos = this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @PostMapping(value = "/images/{postId}" //, produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                         @PathVariable("postId") Integer postId
    ) {
        PostDto updatePostDto;
        try {
            PostDto postDto = postService.getPostById(postId);
            String uploadFileName = fileService.upload(path, image);
            postDto.setImageName(uploadFileName);
             updatePostDto = postService.updatePost(postDto, postId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<PostDto>(updatePostDto, HttpStatus.OK);

    }

    @GetMapping(value = " {imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
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
