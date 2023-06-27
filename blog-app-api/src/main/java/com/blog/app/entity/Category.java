package com.blog.app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
     private long  categoryId;
	
	@NotBlank(message = "Please enter the name")
     private String categoryTitle;
     private String categoryDesc;
     @OneToMany(mappedBy = "category" , cascade=CascadeType.ALL)
     private List<Post> post = new ArrayList<Post>();
     

}
