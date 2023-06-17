package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Category DTO", description = "DTO class for representing a category")
public class CategoryDto {

	@Schema(description = "The ID of the category")
	private Long id;

	@Schema(description = "The name of the category")
	private String name;

	@Schema(description = "The list of subcategories")
	private List<CategoryDto> subcategories;

	// TODO refactor later using lazy loading
	public static CategoryDto convertCategoryToCategoryDtoWithoutDuplication(Category category) {
		List<CategoryDto> subcategories = category.getSubCategories()
				.stream()
				.map(CategoryDto::convertCategoryToCategoryDtoWithoutDuplication)
				.collect(Collectors.toList());
		return new CategoryDto(category.getId(), category.getName(), subcategories);
	}
}
