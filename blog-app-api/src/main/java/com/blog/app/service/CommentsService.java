package com.blog.app.service;

import com.blog.app.payloads.CommentsDto;



public interface CommentsService {
    CommentsDto createComment(CommentsDto commentsDto,Integer postId);
    void  deleteComments(Integer commentsId);

}
