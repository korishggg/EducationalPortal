package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNameSurnameEmailDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;

	public static UserNameSurnameEmailDto convertUserToUserDto(User user) {
		return new UserNameSurnameEmailDto(user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail());
	}
}
