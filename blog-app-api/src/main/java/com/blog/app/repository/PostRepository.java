package com.blog.app.repository;



import com.blog.app.entity.Category;
import com.blog.app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entity.Post;

import java.util.List;

public interface PostRepository extends  JpaRepository<Post, Integer> {


    List<Post> findByUsers(Users users);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}

