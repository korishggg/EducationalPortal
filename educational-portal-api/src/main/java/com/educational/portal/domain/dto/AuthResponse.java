package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Authentication Response", description = "DTO class for authentication response")
public class AuthResponse {

	@Schema(description = "The authentication token")
	private String token;

	@Schema(description = "The refresh token")
	private String refreshToken;
}
