package com.educational.portal.web;

import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.dto.GroupDto;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Tag(name = "Groups", description = "Group management")
@RestController
@RequestMapping("/groups")
public class GroupController {
	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@Operation(
			summary = "Find Group by Id",
			description = "Retrieves a group by its id.",
			tags = {"groups", "find"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = GroupDto.class))}),
			@ApiResponse(responseCode = "404", description = "Not Found: Group not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping("/{id}")
	public ResponseEntity<GroupDto> findById(@PathVariable Long id) {
		Group groupDto = groupService.findById(id);
		return ResponseEntity.ok(GroupDto.convertGroupToGroupDto(groupDto));
	}

	@Operation(
			summary = "Get All Groups",
			description = "Retrieves all groups.",
			tags = {"groups", "get"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = GroupDto.class))}),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping
	public ResponseEntity<List<GroupDto>> getAllGroups() {
		List<GroupDto> groupDtos = groupService.getAllGroups();
		return ResponseEntity.ok(groupDtos);
	}

	@Operation(
			summary = "Create Group",
			description = "Creates a new group.",
			tags = {"groups", "create"})
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Success: Group created"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid group request"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping
	public ResponseEntity<GroupDto> createGroup(Principal principal,
												@RequestBody @Valid CreateGroupRequest createGroupRequest,
												UriComponentsBuilder uriComponentsBuilder) {
		GroupDto group = groupService.createGroup(principal, createGroupRequest);
		UriComponents uriComponents = uriComponentsBuilder.path("/groups/{id}").buildAndExpand(group.getId());
		var location = uriComponents.toUri();

		return ResponseEntity.created(location).build();
	}

	@Operation(
			summary = "Delete Group",
			description = "Deletes a group by its id.",
			tags = {"groups", "delete"})
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Success: Group deleted"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
		groupService.deleteGroupById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Assign Instructor to Group",
			description = "Assigns an instructor to a group.",
			tags = {"groups", "assign"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: Instructor assigned"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("{groupId}/assignInstructor/{instructorId}")
	public ResponseEntity<?> assignInstructorToGroup(@PathVariable(name = "groupId") Long groupId,
													 @PathVariable(name = "instructorId") Long instructorId) {
		groupService.assignInstructorToGroup(groupId, instructorId);
		return ResponseEntity.ok("");
	}

	@Operation(
			summary = "Unassign Instructor from Group",
			description = "Unassigns an instructor from a group.",
			tags = {"groups", "unassign"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: Instructor unassigned"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("{groupId}/unAssignInstructor")
	public ResponseEntity<?> unAssignInstructor(@PathVariable(name = "groupId") Long groupId) {
		groupService.unAssignInstructorFromGroup(groupId);
		return ResponseEntity.ok("");
	}

	@Operation(
			summary = "Assign User to Group",
			description = "Assigns a user to a group.",
			tags = {"groups", "assign"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: User assigned"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("{groupId}/assignUser/{userId}")
	public ResponseEntity<?> assignUsersToGroup(@PathVariable(name = "groupId") Long groupId,
												@PathVariable(name = "userId") Long userId) {
		groupService.assignUserToGroup(groupId, userId);
		return ResponseEntity.ok("");
	}

	@Operation(
			summary = "Unassign User from Group",
			description = "Unassigns a user from a group.",
			tags = {"groups", "unassign"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success: User unassigned"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping("{groupId}/unAssignUser/{userId}")
	public ResponseEntity<?> unAssignUsersToGroup(@PathVariable(name = "groupId") Long groupId,
												  @PathVariable(name = "userId") Long userId) {
		groupService.unAssignUserFromGroup(groupId, userId);
		return ResponseEntity.ok("");
	}
}
