package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Create Category Request", description = "DTO class for creating a category")
public class CreateCategoryRequest {
	@Schema(description = "The name of the category", required = true, example = "Mathematics")
	@NotBlank(message = "Category name should not be empty")
	private String name;
}
