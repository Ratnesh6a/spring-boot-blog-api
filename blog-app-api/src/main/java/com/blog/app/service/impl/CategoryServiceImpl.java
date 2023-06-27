package com.blog.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entity.Category;
import com.blog.app.exception.ResouceNotFoundException;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.repository.CategoryRepo;
import com.blog.app.service.CategoryServiceInterface;

@Service
public class CategoryServiceImpl implements CategoryServiceInterface {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto ctaegoryDto) {
		// TODO Auto-generated method stub
		Category cat = this.modelMapper.map(ctaegoryDto, Category.class);
		Category addCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(addCategory, CategoryDto.class);
	}
    
	/*
	 * public CategoryDto updateCategory(CategoryDto ctaegoryDto, Long
	 * categoryInteger) { // TODO Auto-generated method stub //
	 * this.categoryRepo.findById(null) return null; }
	 */
	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryInteger) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryInteger)
				.orElseThrow(() -> new ResouceNotFoundException("Category","Category Id",categoryInteger));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDesc(categoryDto.getCategoryDesc());
		Category updateedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(updateedCat, CategoryDto.class);
	}
	
	



	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories =this.categoryRepo.findAll();
		List<CategoryDto> allCategory =categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return  allCategory;
	}

	@Override
	public void deleteCategory(Long categoryInteger) {
		// TODO Auto-generated method stub
		Category cat =this.categoryRepo.findById(categoryInteger)
				.orElseThrow(()-> new ResouceNotFoundException("Category","Category Id",categoryInteger));
		this.categoryRepo.delete(cat);
		
		
	}

	@Override
	public CategoryDto getCategoryById(Long categoryInteger) {
		// TODO Auto-generated method stub
		Category cat =this.categoryRepo.findById(categoryInteger)
				.orElseThrow(()-> new ResouceNotFoundException("Category","Category Id",categoryInteger));
		CategoryDto updatedCat = this.modelMapper.map(cat, CategoryDto.class);
		
		return updatedCat;
	}

	

}
