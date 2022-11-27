package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

	@NotBlank(message = "category should not be empty")
	private String name;
}
