package com.blog.app.controller;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.CategoryDto;
import com.blog.app.response.ApiResponse;
import com.blog.app.service.CategoryServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServiceInterface categoryServiceInterface;
	
	//create 
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory =this.categoryServiceInterface.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Long catId ){
		CategoryDto updatedCategory =this.categoryServiceInterface.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/del/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable Long catId ){
		this.categoryServiceInterface.deleteCategory(catId);  
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successful",true),HttpStatus.OK);
		
		
	}
    
	@GetMapping("/get/{catId}")
	public ResponseEntity<CategoryDto> getById(@Valid @PathVariable Long catId ){
		CategoryDto cat =this.categoryServiceInterface.getCategoryById(catId)  ;
		return new ResponseEntity<CategoryDto>(cat,HttpStatus.OK);		
	}
	
	 
		@GetMapping("/getAll")
		public ResponseEntity<List<CategoryDto>> getAllCategories(){
			List<CategoryDto> cat =this.categoryServiceInterface.getCategories()  ;
			return ResponseEntity.ok(cat);
		}
}
