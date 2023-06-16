package com.educational.portal.web;

import com.educational.portal.domain.dto.CategoryDto;
import com.educational.portal.domain.dto.CreateCategoryRequest;
import com.educational.portal.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Tag(name = "Categories", description = "Category management")
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(
			summary = "Find Category by Id",
			description = "Retrieves a category by its id.",
			tags = {"categories", "find"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))}),
			@ApiResponse(responseCode = "404", description = "Not Found: Category not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id,
												@RequestParam(name = "isHideSubCategories", required = false) boolean isHideSubCategories) {
		CategoryDto category = categoryService.findByIdAndConvertToDto(id, isHideSubCategories);
		return ResponseEntity.ok(category);
	}

	@Operation(
			summary = "Get All Categories",
			description = "Retrieves all categories.",
			tags = {"categories", "get"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(value = "isHideSubCategories") boolean isHideSubCategories) {
		List<CategoryDto> categories = categoryService.getAllCategories(isHideSubCategories);
		return ResponseEntity.ok(categories);
	}

	@Operation(
			summary = "Create Category",
			description = "Creates a new category.",
			tags = {"categories", "create"})
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Success: Category created"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid category request"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(Principal principal,
													  @RequestBody @Valid CreateCategoryRequest createCategoryRequest,
													  UriComponentsBuilder uriComponentsBuilder) {
		CategoryDto category = categoryService.createCategory(principal, createCategoryRequest);

		UriComponents uriComponents = uriComponentsBuilder.path("/categories/{id}").buildAndExpand(category.getId());
		var location = uriComponents.toUri();

		return ResponseEntity.created(location).build();
	}

	@Operation(
			summary = "Assign Subcategory to Category",
			description = "Assigns a subcategory to a category.",
			tags = {"categories", "assign"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: Subcategory assigned"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("/{categoryId}/assignSubCategory/{subCategoryId}")
	public ResponseEntity<?> assignSubcategoryToCategory(@PathVariable(name = "categoryId") Long categoryId,
														 @PathVariable(name = "subCategoryId") Long subCategoryId) {
		categoryService.assignSubcategoryToCategory(categoryId, subCategoryId);
		return ResponseEntity.ok("");
	}

	@Operation(
			summary = "Delete Category",
			description = "Deletes a category by its id.",
			tags = {"categories", "delete"})
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Success: Category deleted"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}
}
