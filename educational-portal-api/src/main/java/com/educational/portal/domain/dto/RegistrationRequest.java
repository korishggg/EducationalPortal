package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.validation.ContactNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Registration Request", description = "DTO class for user registration")
public class RegistrationRequest {
	@Schema(description = "The first name of the user", required = true, example = "John")
	@NotBlank(message = "First name should not be null or empty")
	private String firstName;

	@Schema(description = "The last name of the user", required = true, example = "Doe")
	@NotBlank(message = "Last name should not be null or empty")
	private String lastName;

	@Schema(description = "The password for the user", required = true)
	@NotBlank(message = "Password should not be null or empty")
	private String password;

	@Schema(description = "The email address of the user", required = true, example = "johndoe@example.com")
	@Email(message = "Incorrect email syntax")
	@NotBlank(message = "Email should not be null or empty")
	private String email;

	@Schema(description = "The phone number of the user", required = true, example = "1234567890")
	@ContactNumberConstraint
	@NotBlank(message = "Phone number should not be null or empty")
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
