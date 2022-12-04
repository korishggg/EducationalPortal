package com.educational.portal.service;

import com.educational.portal.domain.dto.CategoryDto;
import com.educational.portal.domain.dto.CreateCategoryRequest;
import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final UserService userService;

	public CategoryService(CategoryRepository categoryRepository, UserService userService) {
		this.categoryRepository = categoryRepository;
		this.userService = userService;
	}

	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll()
								 .stream()
								 .map(CategoryDto::convertCategoryToCategoryDto)
								 .collect(Collectors.toList());
	}

	public CategoryDto findByIdAndConvertToDto(Long id, boolean isHideSubCategories) {
		Category category = findById(id);
		if(isHideSubCategories){
			category.setSubCategories(new ArrayList<>());
		}
		return CategoryDto.convertCategoryToCategoryDto(category);
	}

	public CategoryDto createCategory(Principal principal, CreateCategoryRequest createCategoryRequest) {
		createCategoryValidation(createCategoryRequest.getName());
		User user = userService.findByEmail(principal.getName());
		Category category = new Category(createCategoryRequest.getName(), user);
		categoryRepository.save(category);
		return CategoryDto.convertCategoryToCategoryDto(category);
	}

	private void createCategoryValidation(String categoryName) {
		Optional<Category> optionalCategory = categoryRepository.findCategoryByName(categoryName);
		if (optionalCategory.isPresent()) {
			throw new AlreadyExistsException("Category With this name " + categoryName +" already exists");
		}
	}

	public void assignSubcategoryToCategory(Long categoryId, Long subCategoryId) {
		Category category = findById(categoryId);
		Category subCategory = findById(subCategoryId);
		subCategory.setParent(category);
		categoryRepository.save(subCategory);
	}

	private Category findById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Category with this id " + id + " is not found"));
	}

	public void deleteCategoryById(Long id) {
		Category category = findById(id);
		categoryRepository.delete(category);
	}

}
