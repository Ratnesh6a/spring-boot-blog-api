package com.blog.app.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entity.Users;

public interface UserRepository  extends JpaRepository<Users, Integer>{

	 List<Users> findByName(String name);

	Optional<Users> findByEmail(String email);
}
