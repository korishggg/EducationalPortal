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
@Tag(name = "User Info DTO", description = "DTO class for user information")
public class UserInfoDto {
	@Schema(description = "The ID of the user")
	private Long id;

	@Schema(description = "The first name of the user")
	private String firstName;

	@Schema(description = "The last name of the user")
	private String lastName;

	@Schema(description = "The email address of the user")
	private String email;

	@Schema(description = "The phone number of the user")
	private String phone;

	@Schema(description = "Flag indicating if the user is approved")
	private boolean isApproved;

	@Schema(description = "The role of the user")
	private String role;

	@Schema(description = "The IBAN (International Bank Account Number) of the user")
	private String iban;

	public static UserInfoDto convertUserToUserDto(User user) {
		return new UserInfoDto(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPhone(),
				user.isApproved(),
				user.getRole().getName(),
				user.getIban()
		);
	}
}
