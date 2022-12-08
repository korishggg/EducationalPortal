package com.educational.portal.web;

import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.dto.GroupDto;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.service.GroupService;
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

@RestController
@RequestMapping("/groups")
public class GroupController {
	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<GroupDto> findById(@PathVariable Long id) {
		Group groupDto = groupService.findById(id);
		return ResponseEntity.ok(GroupDto.convertGroupToGroupDto(groupDto));
	}

	@GetMapping
	public ResponseEntity<List<GroupDto>> getAllGroups() {
		List<GroupDto> groupDtos = groupService.getAllGroups();
		return ResponseEntity.ok(groupDtos);
	}

	@PostMapping
	public ResponseEntity<GroupDto> createGroup(Principal principal,
												@RequestBody @Valid CreateGroupRequest createGroupRequest,
												UriComponentsBuilder uriComponentsBuilder) {
		GroupDto group = groupService.createGroup(principal, createGroupRequest);
		UriComponents uriComponents = uriComponentsBuilder.path("/groups/{id}").buildAndExpand(group.getId());
		var location = uriComponents.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
		groupService.deleteGroupById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("{groupId}/assignInstructor/{instructorId}")
	public ResponseEntity<?> assignInstructorToGroup(@PathVariable(name = "groupId") Long groupId,
													 @PathVariable(name = "instructorId") Long instructorId) {
		groupService.assignInstructorToGroup(groupId, instructorId);
		return ResponseEntity.ok("");
	}

	@PostMapping("{groupId}/unAssignInstructor")
	public ResponseEntity<?> unAssignInstructor(@PathVariable(name = "groupId") Long groupId) {
		groupService.unAssignInstructorFromGroup(groupId);
		return ResponseEntity.ok("");
	}

	@PostMapping("{groupId}/assignUser/{userId}")
	public ResponseEntity<?> assignUsersToGroup(@PathVariable(name = "groupId") Long groupId,
												@PathVariable(name = "userId") Long userId) {
		groupService.assignUserToGroup(groupId, userId);
		return ResponseEntity.ok("");
	}

	@PostMapping("{groupId}/unAssignUser/{userId}")
	public ResponseEntity<?> unAssignUsersToGroup(@PathVariable(name = "groupId") Long groupId,
												@PathVariable(name = "userId") Long userId) {
		groupService.unAssignUserFromGroup(groupId, userId);
		return ResponseEntity.ok("");
	}
}