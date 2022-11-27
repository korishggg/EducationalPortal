package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private Long id;
	private String name;
	private List<CategoryDto> subcategories;

	public static CategoryDto convertCategoryToCategoryDto(Category category) {
		List<CategoryDto> subcategories = category.getSubCategories()
												  .stream()
												  .map(CategoryDto::convertCategoryToCategoryDto)
												  .collect(Collectors.toList());
		return new CategoryDto(category.getId(), category.getName(), subcategories);
	}
}
