package com.blog.app.service;

import java.util.List;

import com.blog.app.payloads.UsersDto;


public interface UserService {


	UsersDto registerUser(UsersDto usersDto);
	UsersDto createUser(UsersDto usersDto);
	UsersDto updateUser(UsersDto userDto,Integer userId);
	UsersDto getuserById(Integer userId);
	List<UsersDto> getAllUsers();
	void deleteUser(Integer userId);
	List<UsersDto> getuserByName(String userName);
	
	

}
