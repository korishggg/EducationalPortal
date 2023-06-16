package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Group DTO", description = "DTO class for representing a group")
public class GroupDto {
	@Schema(description = "The ID of the group")
	private Long id;

	@Schema(description = "The name of the group")
	private String name;

	@Schema(description = "The category of the group")
	private CategoryDto category;

	@Schema(description = "The list of users in the group")
	private List<UserDto> users;

	@Schema(description = "The instructor of the group")
	private UserDto instructor;

	public static GroupDto convertGroupToGroupDto(Group group) {
		Category category = group.getCategory();
		category.setSubCategories(new ArrayList<>());
		List<UserDto> userDtos = group.getUsers()
				.stream()
				.map(UserDto::convertUserToUserDto)
				.collect(Collectors.toList());
		UserDto userDto = group.getInstructor() != null
				? UserDto.convertUserToUserDto(group.getInstructor())
				: null;
		return new GroupDto(
				group.getId(),
				group.getName(),
				CategoryDto.convertCategoryToCategoryDtoWithoutDuplication(category),
				userDtos,
				userDto);
	}

}