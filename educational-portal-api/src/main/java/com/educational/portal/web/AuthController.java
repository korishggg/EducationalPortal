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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse response = userService.signIn(authRequest);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse response = userService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok().body(response);
    }
}