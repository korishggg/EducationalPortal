package com.educational.portal.service;

import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.dto.GroupDto;
import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.GroupRepository;
import com.educational.portal.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);
	private final GroupRepository groupRepository;
	private final UserService userService;
	private final CategoryService categoryService;

	public GroupService(GroupRepository groupRepository, UserService userService, CategoryService categoryService) {
		this.groupRepository = groupRepository;
		this.userService = userService;
		this.categoryService = categoryService;
	}
	public Group findById(Long id) {
		return groupRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Group with this id = " + id + " is not found"));

	}

	public List<GroupDto> getAllGroups() {
		return groupRepository.findAll()
				.stream()
				.map(GroupDto::convertGroupToGroupDto)
				.collect(Collectors.toList());
	}

	public GroupDto createGroup(Principal principal, CreateGroupRequest createGroupRequest) {
		createGroupValidation(createGroupRequest.getName());
		User userManager = userService.findByEmail(principal.getName());
		Category category = categoryService.findById(createGroupRequest.getCategoryId());
		Group group;
		if (createGroupRequest.getInstructorId() == null) {
			group = new Group(createGroupRequest.getName(), userManager, category);
		} else {
			User userInstructor = userService.findUserById(createGroupRequest.getInstructorId());
			group = new Group(createGroupRequest.getName(), userManager, category, userInstructor);
		}
		groupRepository.save(group);
		return GroupDto.convertGroupToGroupDto(group);
	}

	private void createGroupValidation(String groupName) {
		Optional<Group> optionalGroup = groupRepository.findGroupByName(groupName);
		if (optionalGroup.isPresent()) {
			throw new AlreadyExistsException("Group with this name " + groupName + " already exists");
		}
	}

	public void deleteGroupById(Long id) {
		Group group = findById(id);
		groupRepository.delete(group);
	}

	public void assignInstructorToGroup(Long groupId, Long instructorId) {
		User userInstructor = userService.findUserById(instructorId);
		Group group = findById(groupId);
		if (userInstructor.getRole().getName().equals(Constants.INSTRUCTOR_ROLE) && group.getInstructor() == null) {
			group.setInstructor(userInstructor);
			groupRepository.save(group);
			LOGGER.info("User with id " + instructorId + " is assigned to group with id " + groupId + " as Instructor");
		} else throw new NotAllowedOperationException("User with id " + instructorId + " " +
				"have no instructor role or " + "in this group instructor already assigned");
	}

	public void unAssignInstructorFromGroup(Long groupId) {
		Group group = findById(groupId);
		if (group.getInstructor() != null) {
			group.setInstructor(null);
			groupRepository.save(group);
			LOGGER.info("Instructor has been unassigned from group with id " + groupId);
		} else throw new NotAllowedOperationException("Group with id " + groupId + " is already haven\n't instructor");
	}

	public void assignUserToGroup(Long groupId, Long userId) {
		Group group = findById(groupId);
		User user = userService.findUserById(userId);
		if (user.getRole().getName().equals(Constants.USER_ROLE)) {
			if (group.getUsers().contains(user)) {
				throw new NotAllowedOperationException("User with id " + userId + " is already in the group");
			}
			group.addUser(user);
			groupRepository.save(group);
		} else throw new NotAllowedOperationException("User with id " + userId + " have no user role");
	}

	public void unAssignUserFromGroup(Long groupId, Long userId) {
		Group group = findById(groupId);
		group.getUsers().removeIf(user -> Objects.equals(user.getId(), userId));
		groupRepository.save(group);
	}

}