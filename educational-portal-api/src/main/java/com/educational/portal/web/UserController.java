package com.educational.portal.web;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.ResourceDto;
import com.educational.portal.domain.dto.UserInfoDto;
import com.educational.portal.domain.entity.Resource;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.service.ResourceService;
import com.educational.portal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@PostMapping("/addBankAccount")
	public ResponseEntity<?> addUserBankAccount(Principal principal, @Valid @RequestBody AddBankAccountRequest addBankAccountRequest) {

		userService.addUserBankAccount(principal, addBankAccountRequest);

		return ResponseEntity.ok()
				.build();
	}

	@GetMapping("/userInfo")
	public ResponseEntity<UserInfoDto> userInfo(Principal principal) {
		var userInfo = userService.getUserInfoForAuthorizedUser(principal);
		return ResponseEntity.ok()
				.body(userInfo);
	}

	@GetMapping("/isApproved")
	public ResponseEntity<Boolean> isCurrentUserApproved(Principal principal) {
		var isApproved = userService.isCurrentUserApproved(principal);
		return ResponseEntity.ok()
				.body(isApproved);
	}

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

	@GetMapping("/documentsExists")
	public boolean isResourceExistsForUser(Principal principal) {
		return userService.isResourceExistsForUser(principal);
	}

	@GetMapping("/{userId}/resources")
	public ResponseEntity<List<ResourceDto>> getAllResourcesForUser(@PathVariable Long userId) {
		List<ResourceDto> resources = resourceService.getAllResourcesForUser(userId);
		return ResponseEntity.ok(resources);
	}

}
