package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private boolean isApproved;
	private String role;
	private String iban;

	public static UserInfoDto convertUserToUserDto(User user) {
		return new UserInfoDto(user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPhone(),
				user.isApproved(),
				user.getRole().getName(),
				user.getIban());
	}

}
