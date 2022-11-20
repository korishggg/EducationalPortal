package com.educational.portal.web;

import com.educational.portal.service.UserService;
import com.educational.portal.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/assignManager/{id}")
	public ResponseEntity<?> assignManager(@PathVariable Long id) {
		userService.assignManagerByUserId(id);
		return ResponseEntity.ok("User with this id = " + id + " been assigned with " + Constants.MANAGER_ROLE + " role");
	}
}
