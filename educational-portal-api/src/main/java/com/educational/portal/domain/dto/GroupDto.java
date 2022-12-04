package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
	private Long id;
	private String name;
	private CategoryDto category;
	private List<UserDto> users;
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
				CategoryDto.convertCategoryToCategoryDto(category),
				userDtos,
				userDto);
	}

}