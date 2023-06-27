package com.blog.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.blog.app.payloads.CategoryDto;

public interface CategoryServiceInterface {
	
	
	
	// create 
	public CategoryDto createCategory(CategoryDto ctaegoryDto);
	
	//update
	public CategoryDto updateCategory(CategoryDto ctaegoryDto, Long categoryInteger);
	
	//delete 
	
	public void deleteCategory(Long categoryInteger);
	
	//getCategoryId
	
	public CategoryDto getCategoryById(Long categoryInteger);
	
	
	//getAllCategory
	
	public List<CategoryDto> getCategories();

	
	
	

}
