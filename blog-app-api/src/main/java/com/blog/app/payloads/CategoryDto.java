package com.blog.app.payloads;

import com.blog.app.entity.Post;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private long  categoryId;
	
	@NotBlank(message = "Please enter the name")
     private String categoryTitle;
	
	
     private String categoryDesc;


     


     
     

}
