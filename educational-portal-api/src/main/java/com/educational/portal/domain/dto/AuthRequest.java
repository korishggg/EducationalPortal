package com.educational.portal.domain.dto;

import javax.validation.constraints.NotBlank;

public class AuthRequest {

	@NotBlank(message = "email should not be empty")
	private String email;
	@NotBlank(message = "password should not be empty")
	private String password;

	public AuthRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
