package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.validation.ContactNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

	@NotBlank(message = "first name should not be null or empty")
	private String firstName;
	@NotBlank(message = "last name should not be null or empty")
	private String lastName;
	@NotBlank(message = "password should not be null or empty")
	private String password;
	@Email(message = "Incorrect email syntax")
	@NotBlank(message = "email should not be null or empty")
	private String email;
	@ContactNumberConstraint
	@NotBlank(message = "phone number should not be null or empty")
	private String phone;

	public static User convertRegistrationRequestToUser(RegistrationRequest registrationRequest,
														Role userRole,
														PasswordEncoder passwordEncoder) {
		return new User(registrationRequest.getFirstName(),
				registrationRequest.getLastName(),
				passwordEncoder.encode(registrationRequest.getPassword()),
				registrationRequest.getEmail(),
				registrationRequest.getPhone(),
				userRole);
	}
}
