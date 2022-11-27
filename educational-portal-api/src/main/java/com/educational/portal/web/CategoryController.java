package com.educational.portal.web;

import com.educational.portal.domain.dto.CategoryDto;
import com.educational.portal.domain.dto.CreateCategoryRequest;
import com.educational.portal.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
		CategoryDto category = categoryService.findById(id);
		return ResponseEntity.ok(category);
	}

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(Principal principal,
													  @RequestBody @Valid CreateCategoryRequest createCategoryRequest,
													  UriComponentsBuilder uriComponentsBuilder) {
		CategoryDto category = categoryService.createCategory(principal, createCategoryRequest);

		UriComponents uriComponents = uriComponentsBuilder.path("/categories/{id}").buildAndExpand(category.getId());
		var location = uriComponents.toUri();

		return ResponseEntity.created(location).build();
	}
}
