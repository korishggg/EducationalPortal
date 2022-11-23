package com.educational.portal.web;

import com.educational.portal.domain.dto.AuthRequest;
import com.educational.portal.domain.dto.AuthResponse;
import com.educational.portal.domain.dto.RefreshTokenRequest;
import com.educational.portal.domain.dto.RegistrationRequest;
import com.educational.portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        AuthResponse response = userService.signIn(authRequest);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = userService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(response);
    }
}