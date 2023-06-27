package com.blog.app.entity;




import com.blog.app.payloads.CommentsDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "postId")
	private Integer postId;
	@NotBlank(message="Title Cannnot be blank")
	private String title;
	@NotBlank(message="Content Cannnot be blank")
	private String content;
	@NotBlank
	private String imageName;
	private Date addedDate;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne
	@JoinColumn(name = "id")
	private Users users;
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comments> comments = new ArrayList<>();


}
