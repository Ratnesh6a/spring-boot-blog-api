package com.blog.app.service.impl;

import com.blog.app.entity.Comments;
import com.blog.app.entity.Post;
import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.payloads.CommentsDto;
import com.blog.app.repository.CommentsRepository;
import com.blog.app.repository.PostRepository;
import com.blog.app.service.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CommentsDto createComment(CommentsDto commentsDto, Integer postId) {
        Post postDetails = postRepository.findById(postId).orElseThrow(() -> new ResouceNotFoundException("Post", "PostID", postId));
        Comments commentsMap = this.modelMapper.map(commentsDto, Comments.class);
        commentsMap.setPost(postDetails);
        Comments saveComments = this.commentsRepository.save(commentsMap);
        CommentsDto CommetsDtoMap = this.modelMapper.map(saveComments, CommentsDto.class);
        return CommetsDtoMap;
    }

    @Override
    public void deleteComments(Integer commentsId) {
        Comments comments = commentsRepository.findById(commentsId).orElseThrow(() -> new ResouceNotFoundException("Comments", "Comments", commentsId));
        commentsRepository.delete(comments);

    }
}
