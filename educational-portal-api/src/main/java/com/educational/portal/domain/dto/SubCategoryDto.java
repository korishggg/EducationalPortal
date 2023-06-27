package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Category DTO", description = "DTO class for representing a category")
public class SubCategoryDto {

	@Schema(description = "The ID of the Subcategory")
	private Long id;

	@Schema(description = "The name of the Subcategory")
	private String name;

	private String parentName;


	public static SubCategoryDto convertSubCategoryToSubCategoryDtoWithoutDuplication(Category category) {
		return new SubCategoryDto(category.getId(), category.getName(), category.getParent().getName());
	}
}