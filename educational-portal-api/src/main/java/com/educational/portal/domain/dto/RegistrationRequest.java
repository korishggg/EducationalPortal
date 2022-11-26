package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.validation.ContactNumberConstraint;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

	public RegistrationRequest(String firstName, String lastName, String password, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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
