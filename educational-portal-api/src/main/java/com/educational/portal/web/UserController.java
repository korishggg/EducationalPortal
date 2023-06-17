package com.educational.portal.web;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.domain.dto.ResourceDto;
import com.educational.portal.domain.dto.UserInfoDto;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.service.ResourceService;
import com.educational.portal.domain.dto.UserNameSurnameEmailDto;
import com.educational.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final ResourceService resourceService;

	public UserController(UserService userService, ResourceService resourceService) {
		this.userService = userService;
		this.resourceService = resourceService;
	}

	@Operation(
			summary = "Add User Bank Account",
			description = "Adds a bank account for the authenticated user.",
			tags = {"users", "bankAccount"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: User bank account added"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("/addBankAccount")
	public ResponseEntity<?> addUserBankAccount(
			Principal principal,
			@Valid @RequestBody AddBankAccountRequest addBankAccountRequest) {
		userService.addUserBankAccount(principal, addBankAccountRequest);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Get User Info",
			description = "Retrieves the user information for the authenticated user.",
			tags = {"users", "userInfo"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Retrieved user info",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDto.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal Server Error"
			)
	})
	@GetMapping("/userInfo")
	public ResponseEntity<UserInfoDto> userInfo(Principal principal) {
		var userInfo = userService.getUserInfoForAuthorizedUser(principal);
		return ResponseEntity.ok().body(userInfo);
	}

	@Operation(
			summary = "Check if Current User is Approved",
			description = "Checks if the current user is approved.",
			tags = {"users", "approval"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Retrieved current user approval status",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal Server Error"
			)
	})
	@GetMapping("/isApproved")
	public ResponseEntity<Boolean> isCurrentUserApproved(Principal principal) {
		var isApproved = userService.isCurrentUserApproved(principal);
		return ResponseEntity.ok().body(isApproved);
	}

	@Operation(
			summary = "Search Users by Email or Surname",
			description = "Searches users by email or surname within a specific group.",
			tags = {"users", "search"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Retrieved matching users",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserNameSurnameEmailDto.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal Server Error"
			)
	})
	@GetMapping(value = "/search/{groupId}")
	public ResponseEntity<List<UserNameSurnameEmailDto>> findByEmailOrSurname(@RequestParam(value = "findByEmailOrSurname") String findByEmailOrSurname,
																			  @PathVariable Long groupId) {
		List<UserNameSurnameEmailDto> users = userService.findUsersByEmailOrLastName(findByEmailOrSurname, groupId);
		return ResponseEntity.ok(users);
	}

	@Operation(
			summary = "Get All Instructors",
			description = "Retrieves a list of all instructors.",
			tags = {"users", "instructors"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Retrieved all instructors",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal Server Error"
			)
	})
	@GetMapping("/instructors")
	public ResponseEntity<List<UserDto>> getAllInstructors() {
		List<UserDto> instructors = userService.getAllInstructors();
		return ResponseEntity.ok(instructors);
	}

	@Operation(
			summary = "Upload Documents",
			description = "Uploads passport and tax ID documents for the authenticated user.",
			tags = {"users", "documents"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: Files uploaded"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("/uploadDocuments")
	public ResponseEntity<String> uploadData(Principal principal,
											 @RequestParam(value = "passportFiles") MultipartFile[] passportFiles,
											 @RequestParam(value = "taxIdFiles") MultipartFile[] taxIdFiles) {
		try {
			userService.uploadData(principal, passportFiles, taxIdFiles);
			return ResponseEntity.ok("Files uploaded successfully");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotAllowedOperationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during file upload.");
		}
	}

	@Operation(
			summary = "Check if Resource Exists for User",
			description = "Checks if there is a resource exists for the authenticated user.",
			tags = {"users", "resources"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Resource exists",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))
			)
	})
	@GetMapping("/documentsExists")
	public boolean isResourceExistsForUser(Principal principal) {
		return userService.isResourceExistsForUser(principal);
	}

	@Operation(
			summary = "Get All Resources for User",
			description = "Retrieves all resources for a user by their ID.",
			tags = {"users", "resources"})
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Success: Retrieved all resources for user",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceDto.class))
			),
			@ApiResponse(
					responseCode = "500",
					description = "Internal Server Error"
			)
	})
	@GetMapping("/{userId}/resources")
	public ResponseEntity<List<ResourceDto>> getAllResourcesForUser(@PathVariable Long userId) {
		List<ResourceDto> resources = resourceService.getAllResourcesForUser(userId);
		return ResponseEntity.ok(resources);
	}

}
