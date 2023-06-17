package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "User Name Surname Email DTO", description = "DTO class for user name, surname, and email")
public class UserNameSurnameEmailDto {

	@Schema(description = "The ID of the user")
	private Long id;

	@Schema(description = "The first name of the user")
	private String firstName;

	@Schema(description = "The last name of the user")
	private String lastName;

	@Schema(description = "The email address of the user")
	private String email;

	public static UserNameSurnameEmailDto convertUserToUserDto(User user) {
		return new UserNameSurnameEmailDto(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail()
		);
	}
}
