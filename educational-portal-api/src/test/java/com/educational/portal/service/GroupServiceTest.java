package com.educational.portal.service;

import com.educational.portal.TestConstants;
import com.educational.portal.domain.dto.CreateGroupRequest;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private UserService userService;
	@Mock
	private CategoryService categoryService;
	private GroupService groupService;

	Long groupId = 1L;

	@BeforeEach
	void setUp() {
		groupService = new GroupService(groupRepository, userService, categoryService);

	}

	public void MockGroupFindById() {
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITH_INSTRUCTOR));
	}

	@Test
	void findById() {
		MockGroupFindById();

		Group returnedGroup = groupService.findById(groupId);

		assertEquals(returnedGroup, TestConstants.GROUP_WITH_INSTRUCTOR);
	}

	@Test
	void findByIdWhenUserNotFound() {
		when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

		Throwable groupNotFoundException = assertThrows(NotFoundException.class, () -> groupService.findById(groupId));

		assertEquals("Group with this id = " + groupId + " is not found", groupNotFoundException.getMessage());
	}

	@Test
	void getAllGroups() {
		groupService.getAllGroups();

		verify(groupRepository).findAll();
	}

	@Test
	void createGroupWhenGroupNameIsExists() {
		Principal principal = mock(Principal.class);

		var groupName = "name";

		Group group = new Group(groupName, TestConstants.USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY);

		CreateGroupRequest createGroupRequest = new CreateGroupRequest("name", TestConstants.CATEGORY.getId(), TestConstants.USER_WITH_INSTRUCTOR_ROLE.getId());

		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.of(group));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> groupService.createGroup(principal, createGroupRequest));
		assertEquals("Group with this name " + groupName + " already exists", alreadyExistsException.getMessage());
	}

	@Test
	void createGroupWhenInstructorNull() {
		Principal principal = mock(Principal.class);

		var groupName = TestConstants.GROUP_WITHOUT_INSTRUCTOR.getName();

		CreateGroupRequest createGroupRequest = new CreateGroupRequest(groupName, groupId, groupId);

		when(principal.getName()).thenReturn(TestConstants.USER_WITH_MANAGER_ROLE.getEmail());
		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.empty());
		when(categoryService.findById(groupId)).thenReturn(TestConstants.CATEGORY);

		groupService.createGroup(principal, createGroupRequest);

		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void createGroupWhenInstructorIsNotNull() {
		Principal principal = mock(Principal.class);

		var groupName = TestConstants.GROUP_WITH_INSTRUCTOR.getName();

		CreateGroupRequest createGroupRequest = new CreateGroupRequest(groupName, groupId, groupId);

		when(principal.getName()).thenReturn(TestConstants.USER_WITH_MANAGER_ROLE.getEmail());
		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.empty());
		when(categoryService.findById(groupId)).thenReturn(TestConstants.CATEGORY);
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);

		groupService.createGroup(principal, createGroupRequest);

		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void deleteGroupById() {
		MockGroupFindById();

		groupService.deleteGroupById(groupId);

		verify(groupRepository).delete(TestConstants.GROUP_WITH_INSTRUCTOR);
	}

	@Test
	void assignInstructorToGroup() {
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITHOUT_INSTRUCTOR));

		groupService.assignInstructorToGroup(groupId, groupId);

		verify(groupRepository).save(any(Group.class));

	}

	@Test
	void assignInstructorToGroupWhenInstructorIsNotNull() {
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITH_INSTRUCTOR));

		TestConstants.GROUP_WITH_INSTRUCTOR.setInstructor(TestConstants.USER_WITH_INSTRUCTOR_ROLE);

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> groupService.assignInstructorToGroup(groupId, groupId));
		assertEquals("User with id " + groupId + " have no instructor role or " +
				"in this group instructor already assigned", alreadyExistsException.getMessage());
	}

	@Test
	void assignInstructorToGroupWhenUserHaveNoInstructorRole() {
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_USER_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITHOUT_INSTRUCTOR));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> groupService.assignInstructorToGroup(groupId, groupId));
		assertEquals("User with id " + groupId + " have no instructor role or " +
				"in this group instructor already assigned", alreadyExistsException.getMessage());
	}

	@Test
	void unAssignInstructorFromGroup() {
		MockGroupFindById();

		groupService.unAssignInstructorFromGroup(groupId);

		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void unAssignInstructorFromGroupWhenInstructorIsNull() {
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITHOUT_INSTRUCTOR));

		TestConstants.GROUP_WITHOUT_INSTRUCTOR.setInstructor(null);

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class, () -> groupService.unAssignInstructorFromGroup(groupId));

		assertEquals("Group with id " + groupId + " is already have not instructor", notAllowedOperationException.getMessage());
	}

	@Test
	void assignUserToGroup() {
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_USER_ROLE);
		MockGroupFindById();

		groupService.assignUserToGroup(groupId, groupId);

		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void assignUserToGroupWhenUserHaveNoUserRole() {
		when(userService.findUserById(groupId)).thenReturn(TestConstants.USER_WITH_MANAGER_ROLE);
		MockGroupFindById();

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class, () -> groupService.assignUserToGroup(groupId, groupId));

		assertEquals("User with id " + groupId + " have no user role", notAllowedOperationException.getMessage());

	}

	@Test
	void unAssignUserFromGroup() {
		Group group = new Group("name", TestConstants.USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY);
		User user = new User("firstName", "lastName", "password", "email", "phone", TestConstants.USER_ROLE);

		user.setId(groupId);
		group.addUser(user);

		when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

		groupService.unAssignUserFromGroup(groupId, groupId);

		verify(groupRepository).save(group);
		assertEquals(0, group.getUsers().size());
	}
}