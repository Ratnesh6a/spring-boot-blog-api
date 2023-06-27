package com.blog.app.service.impl;

import com.blog.app.entity.Category;
import com.blog.app.entity.Post;
import com.blog.app.entity.Users;
import com.blog.app.exception.GlobalExceptionHandler;
import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.payloads.PostDto;
import com.blog.app.repository.CategoryRepo;
import com.blog.app.repository.PostRepository;
import com.blog.app.repository.UserRepository;
import com.blog.app.response.PostResponses;
import com.blog.app.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   @Autowired
   private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Long categoryId) {
        Users users = this.userRepository.findById(userId).orElseThrow(()->new ResouceNotFoundException("Users",
                "userId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResouceNotFoundException("Category",
                "categoryId", categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUsers(users);
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);
        PostDto postAllData = this.modelMapper.map(newPost, PostDto.class);
        return postAllData;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResouceNotFoundException("Post", "PostId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post savePost = this.postRepository.save(post);
        PostDto savepostDto = this.modelMapper.map(savePost, PostDto.class);
        return savepostDto;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResouceNotFoundException("Post", "PostId", postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> findAll = this.postRepository.findAll();
        List<PostDto> postDto = findAll.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public PostResponses getAllPostPerPage(Integer pageNumber, Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPost = pagePost.getContent();


       List<PostDto> postDto = allPost.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        if (postDto == null || postDto.isEmpty()) {
  //          GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
       PostResponses postResponses =  new PostResponses();
        postResponses.setContent(postDto);
        postResponses.setPageNumber(pagePost.getNumber());
        postResponses.setPageSize(pagePost.getSize());
        postResponses.setTotalElements(postResponses.getTotalElements());
        postResponses.setTotalPages(postResponses.getTotalPages());
        postResponses.setLastPage(pagePost.isLast());
        return postResponses;
    }


    @Override
    public PostResponses getAllPostPageAndSort(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPost = pagePost.getContent();


        List<PostDto> postDto = allPost.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        if (postDto == null || postDto.isEmpty()) {
            //          GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        PostResponses postResponses =  new PostResponses();
        postResponses.setContent(postDto);
        postResponses.setPageNumber(pagePost.getNumber());
        postResponses.setPageSize(pagePost.getSize());
        postResponses.setTotalElements(postResponses.getTotalElements());
        postResponses.setTotalPages(postResponses.getTotalPages());
        postResponses.setLastPage(pagePost.isLast());
        return postResponses;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResouceNotFoundException("Post", "PostId", postId));
        PostDto  postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResouceNotFoundException("Category", "categoryId", categoryId));
        List<Post> byCategory = this.postRepository.findByCategory(category);
        List<PostDto> postDtosList = byCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtosList;
    }
// Get Post By Users
    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        Users userbyId = this.userRepository.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User", "userId", userId));
        List<Post> findbyUsers = this.postRepository.findByUsers(userbyId);
       /* if(findbyUsers.isEmpty() || findbyUsers == null){
            throw new ResouceNotFoundException("User","userId",userId);
        }*/
        List<PostDto> collectPostDto = findbyUsers.stream().map((post -> modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        if(collectPostDto.isEmpty() || collectPostDto == null){
            throw new ResouceNotFoundException("User Data","userId",userId);
        }
        return collectPostDto;
    }
// Searching
    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepository.findByTitleContaining(keyword);
        List<PostDto> searchByTitle = posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return searchByTitle;
    }


}
