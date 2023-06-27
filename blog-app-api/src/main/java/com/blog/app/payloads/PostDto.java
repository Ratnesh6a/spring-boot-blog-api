package com.blog.app.payloads;

import com.blog.app.entity.Category;
import com.blog.app.entity.Comments;
import com.blog.app.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {

	@NotBlank(message="Title Cannnot be blank")
	private String title;
	@NotBlank(message="Content Cannnot be blank")
	private String content;
	@NotBlank
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UsersDto users;
	private List<CommentsDto> comments = new ArrayList<>();

}
