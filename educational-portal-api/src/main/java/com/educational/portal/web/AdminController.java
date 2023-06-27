package com.educational.portal.web;

import com.educational.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Admin", description = "Admin managment")
@RestController
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@Operation(
			summary = "Assign Manager Role to User",
			description = "Assigns the Manager role to a user specified by their id.",
			tags = {"admin", "assign", "manager"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: Manager role assigned"),
			@ApiResponse(responseCode = "404", description = "Not Found: User not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("/assignManager/{id}")
	public ResponseEntity<?> assignManager(@PathVariable Long id) {
		userService.assignManagerByUserId(id);
		return ResponseEntity.ok().build();
	}
}
