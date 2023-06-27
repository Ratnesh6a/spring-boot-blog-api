package com.blog.app.service.impl;

import java.security.PrivateKey;
import java.util.List;

import java.util.stream.Collectors;

import com.blog.app.entity.Role;
import com.blog.app.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.entity.Users;
import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.payloads.UsersDto;
import com.blog.app.repository.UserRepository;
import com.blog.app.service.UserService;

@Service
public class UserServiceIpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UsersDto registerUser(UsersDto usersDto) {
		Users users = modelMapper.map(usersDto, Users.class);
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		// Assign Roles

		Role role = this.roleRepository.findById(1).get();
		users.getRoles().add(role);
		Users newUsers = this.userRepo.save(users);
		return this.modelMapper.map(newUsers,UsersDto.class);
	}

	@Override
	public UsersDto createUser(UsersDto usersDto) {
		// TODO Auto-generated method stub
		Users users = this.dtoToUser(usersDto);
		Users saveUsers = this.userRepo.save(users);
		return this.userToDto(saveUsers);
	}

	@Override
	public UsersDto updateUser(UsersDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		
		Users users = this.userRepo.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User","Id",userId));
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		users.setAbout(userDto.getAbout());
		
		Users updateUser = this.userRepo.save(users);
		UsersDto userDto1 = this.userToDto(updateUser);
		return userDto1;
	}

	@Override
	public UsersDto getuserById(Integer userId) {
		// TODO Auto-generated method stub
		Users users = this.userRepo.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User","Id",userId));
		
		
		return this.userToDto(users);
	}
	
	public List<UsersDto> getuserByName(String name) {
		// TODO Auto-generated method stub
		 List<Users> users =this.userRepo.findByName(name);
		
		 List<UsersDto> userDto =users.stream().map(a->this.userToDto(a)).collect(Collectors.toList());
		
		return userDto; 
	}
	
	

	@Override
	public List<UsersDto> getAllUsers() {
		List<Users> users = this.userRepo.findAll();
		
		List<UsersDto> usersDtos = users.stream().map(a->this.userToDto(a)).collect(Collectors.toList());
		return usersDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		Users users = this.userRepo.findById(userId).orElseThrow(() -> new ResouceNotFoundException("User","Id",userId));
		this.userRepo.delete(users);

	}
	
	
	// Model mapper or method
	
	private Users dtoToUser(UsersDto usersDto)
	{
		Users users = this.modelMapper.map(usersDto, Users.class);
		
	//	Users users = new Users();
		/*
		 * users.setId(userDto.getId()); users.setName(userDto.getName());
		 * users.setEmail(userDto.getEmail()); users.setPassword(userDto.getPassword());
		 * users.setAbout(userDto.getAbout());
		 */
		return users;
		
	}
	
	public UsersDto userToDto(Users users) {
		
		UsersDto usersDto = this.modelMapper.map(users, UsersDto.class);
		/*
		 * UserDto userDto = new UserDto(); userDto.setId(users.getId());
		 * userDto.setName(users.getName()); userDto.setEmail(users.getEmail());
		 * userDto.setPassword(users.getPassword()); userDto.setAbout(users.getAbout());
		 */
		return usersDto;
		
	}


    public static class PostServiceImpl {
    }
}
