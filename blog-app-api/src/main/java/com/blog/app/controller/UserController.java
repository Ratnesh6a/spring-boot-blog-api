package com.blog.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.UsersDto;
import com.blog.app.response.ApiResponse;
import com.blog.app.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userSerrvice;
	
	// post Controller 
	@PostMapping("/addName")																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						
	public ResponseEntity<UsersDto> createUsers(@Valid @RequestBody UsersDto userDto){
		UsersDto createUserDto = this.userSerrvice.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	
	}
	
	
	// put update user
	@PutMapping("/update/{userid}")
	ResponseEntity<UsersDto> updateUser( @Valid @RequestBody UsersDto userDto, @PathVariable("userid") Integer userid ){
		
		UsersDto updateuserDto =this.userSerrvice.updateUser(userDto, userid);
		return ResponseEntity.ok(updateuserDto);
		
	}
	
	
	//Delete user
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?>deleteUserbyId(@Valid @PathVariable("userId") Integer userId){
		this.userSerrvice.deleteUser(userId);
		return new ResponseEntity(new ApiResponse("User Deleted" , true),HttpStatus.FOUND);
		
	}
	
	
	
	// Get User
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UsersDto>> getAllUserDetails(){
		return ResponseEntity.ok().body(this.userSerrvice.getAllUsers());
		
	}
	
	
	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<UsersDto> getUserDetailsbyId(@Valid @PathVariable Integer userId){
		return ResponseEntity.ok().body(this.userSerrvice.getuserById(userId));
		
	}
	
	@GetMapping("/getByName/{userName}")
	public ResponseEntity<List<UsersDto>> getUserDetailsbyName(@Valid @PathVariable String userName){
		return ResponseEntity.ok(this.userSerrvice.getuserByName(userName));
		
	}
	
   
}
