package com.educational.portal.web;

import com.educational.portal.domain.dto.AuthRequest;
import com.educational.portal.domain.dto.AuthResponse;
import com.educational.portal.domain.dto.RefreshTokenRequest;
import com.educational.portal.domain.dto.RegistrationRequest;
import com.educational.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Auth", description = "Authentication management")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user with the provided registration information.",
            tags = {"auth", "register"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success: User registered"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid registration information"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "User Sign In",
            description = "Authenticates a user with the provided credentials and generates an access token.",
            tags = {"auth", "login"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid authentication request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse response = userService.signIn(authRequest);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Refresh Access Token",
            description = "Refreshes the access token using a valid refresh token.",
            tags = {"auth", "refresh"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid refresh token request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid refresh token"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = userService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(response);
    }
}