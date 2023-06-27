package com.blog.app.service;

import com.blog.app.entity.Post;
import com.blog.app.payloads.PostDto;
import com.blog.app.response.PostResponses;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Long categoryId );

    PostDto updatePost(PostDto postDto ,Integer postId);

    void   deletePost(Integer postId);

    List<PostDto> getAllPost();

     PostResponses getAllPostPerPage(Integer pageNumber, Integer pageSize);


    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPost (String keyword);


    PostResponses getAllPostPageAndSort(Integer pageNumber, Integer pageSize, String sortBy);
}
