package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Refresh Token Request", description = "DTO class for refreshing an access token")
public class RefreshTokenRequest {
    @Schema(description = "The refresh token", example = "xxxxxxxxxxxx")
    private String refreshToken;
}
