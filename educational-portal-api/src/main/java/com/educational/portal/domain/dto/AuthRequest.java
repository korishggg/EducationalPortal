package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

	@NotBlank(message = "email should not be empty")
	private String email;
	@NotBlank(message = "password should not be empty")
	private String password;
}
