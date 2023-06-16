package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Authentication Request", description = "DTO class for authentication request")
public class AuthRequest {
	@Schema(description = "The email address", required = true, example = "user@example.com")
	@NotBlank(message = "Email should not be empty")
	private String email;

	@Schema(description = "The password", required = true, example = "password123")
	@NotBlank(message = "Password should not be empty")
	private String password;
}
