package com.blog.app.controller;

import com.blog.app.entity.Comments;
import com.blog.app.payloads.CommentsDto;
import com.blog.app.response.ApiResponse;
import com.blog.app.service.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentsDto> createComments(@RequestBody CommentsDto commentsDto, @PathVariable Integer postId){
        CommentsDto comment = commentsService.createComment(commentsDto, postId);

        return new ResponseEntity<CommentsDto>(comment,HttpStatus.OK  );
    }

    @DeleteMapping("{commentsId}")
    public  ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer commentsId)
    {
        this.commentsService.deleteComments(commentsId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Comments Deleted Successfully",true), HttpStatus.OK);
    }
}
