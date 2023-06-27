 package com.blog.app.payloads;


 import com.blog.app.entity.Post;
 import com.blog.app.entity.Role;
 import jakarta.persistence.Table;
 import lombok.*;

 import java.util.ArrayList;
 import java.util.List;


 @Getter
 @Setter
 @AllArgsConstructor
 @NoArgsConstructor

public class UsersDto {
	
   private int id;
	
	
	private String name;
	
	private String email;
	
	private String password;
	
	
	private String about;

     private  List<RoleDto> roles = new ArrayList<>();
	
	



}
