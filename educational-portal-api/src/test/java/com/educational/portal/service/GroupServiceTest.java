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

import static com.educational.portal.TestConstants.USER_WITH_MANAGER_ROLE;
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

	public void mockGroupFindById() {
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITH_INSTRUCTOR));
	}

	@Test
	void findById() {
		mockGroupFindById();

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

		Group group = new Group(groupName, USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY);

		CreateGroupRequest createGroupRequest = new CreateGroupRequest("name", TestConstants.CATEGORY.getId(), TestConstants.USER_WITH_INSTRUCTOR_ROLE.getId());

		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.of(group));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> groupService.createGroup(principal, createGroupRequest));
		assertEquals("Group with this name " + groupName + " already exists", alreadyExistsException.getMessage());
	}

	@Test
	void createGroupWhenInstructorNull() {
		Principal principal = mock(Principal.class);

		var groupName = TestConstants.GROUP_WITHOUT_INSTRUCTOR.getName();
		var categoryId = 2L;
		var instructorId = 3L;

		CreateGroupRequest createGroupRequest = new CreateGroupRequest(groupName, categoryId, instructorId);

		when(principal.getName()).thenReturn(USER_WITH_MANAGER_ROLE.getEmail());
		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.empty());
		when(categoryService.findById(categoryId)).thenReturn(TestConstants.CATEGORY);

		groupService.createGroup(principal, createGroupRequest);

		verify(userService).findByEmail(USER_WITH_MANAGER_ROLE.getEmail());
		verify(categoryService).findById(categoryId);
		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void createGroupWhenInstructorIsNotNull() {
		Principal principal = mock(Principal.class);

		var groupName = TestConstants.GROUP_WITH_INSTRUCTOR.getName();
		var categoryId = 2L;
		var instructorId = 3L;

		CreateGroupRequest createGroupRequest = new CreateGroupRequest(groupName, categoryId, instructorId);

		when(principal.getName()).thenReturn(USER_WITH_MANAGER_ROLE.getEmail());
		when(groupRepository.findGroupByName(groupName)).thenReturn(Optional.empty());
		when(categoryService.findById(categoryId)).thenReturn(TestConstants.CATEGORY);
		when(userService.findUserById(instructorId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);

		groupService.createGroup(principal, createGroupRequest);

		verify(userService).findByEmail(USER_WITH_MANAGER_ROLE.getEmail());
		verify(categoryService).findById(categoryId);
		verify(userService).findUserById(instructorId);
		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void deleteGroupById() {
		mockGroupFindById();

		groupService.deleteGroupById(groupId);

		verify(groupRepository).delete(TestConstants.GROUP_WITH_INSTRUCTOR);
	}

	@Test
	void assignInstructorToGroup() {
		var instructorId = 3L;

		Group group = new Group("name", USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY);

		when(userService.findUserById(instructorId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

		groupService.assignInstructorToGroup(groupId, instructorId);

		verify(userService).findUserById(instructorId);
		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void assignInstructorToGroupWhenGroupHasInstructor() {
		var instructorId = 3L;

		when(userService.findUserById(instructorId)).thenReturn(TestConstants.USER_WITH_INSTRUCTOR_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITH_INSTRUCTOR));

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class,
				() -> groupService.assignInstructorToGroup(groupId, instructorId));
		assertEquals("User with id " + instructorId + " have no instructor role or " +
				"in this group instructor already assigned", notAllowedOperationException.getMessage());
		verify(userService).findUserById(instructorId);
	}

	@Test
	void assignInstructorToGroupWhenUserHaveNoInstructorRole() {
		var userId = 3L;

		when(userService.findUserById(userId)).thenReturn(TestConstants.USER_WITH_USER_ROLE);
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITHOUT_INSTRUCTOR));

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class,
				() -> groupService.assignInstructorToGroup(groupId, userId));
		assertEquals("User with id " + userId + " have no instructor role or " +
				"in this group instructor already assigned", notAllowedOperationException.getMessage());
		verify(userService).findUserById(userId);
	}

	@Test
	void unAssignInstructorFromGroup() {
		var group = new Group("test1", TestConstants.USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY, TestConstants.USER_WITH_INSTRUCTOR_ROLE);

		when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
		groupService.unAssignInstructorFromGroup(groupId);

		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void unAssignInstructorFromGroupWhenInstructorIsNull() {
		when(groupRepository.findById(groupId)).thenReturn(Optional.of(TestConstants.GROUP_WITHOUT_INSTRUCTOR));

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class, () -> groupService.unAssignInstructorFromGroup(groupId));

		assertEquals("Group with id " + groupId + " is already haven\n't instructor", notAllowedOperationException.getMessage());
	}

	@Test
	void assignUserToGroup() {
		var userId = 4L;

		mockGroupFindById();
		when(userService.findUserById(userId)).thenReturn(TestConstants.USER_WITH_USER_ROLE);

		groupService.assignUserToGroup(groupId, userId);

		verify(groupRepository).findById(groupId);
		verify(userService).findUserById(userId);
		verify(groupRepository).save(any(Group.class));
	}

	@Test
	void assignUserToGroupWhenUserHaveNoUserRole() {
		var userId = 4L;

		mockGroupFindById();
		when(userService.findUserById(userId)).thenReturn(USER_WITH_MANAGER_ROLE);
		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class, () -> groupService.assignUserToGroup(groupId, userId));

		assertEquals("User with id " + userId + " have no user role", notAllowedOperationException.getMessage());

		verify(groupRepository).findById(groupId);
		verify(userService).findUserById(userId);
	}

	@Test
	void unAssignUserFromGroup() {
		var userId = 4L;
		Group group = new Group("name", USER_WITH_MANAGER_ROLE, TestConstants.CATEGORY);
		User user = new User("firstName", "lastName", "password", "email", "phone", TestConstants.USER_ROLE);
		user.setId(userId);
		group.addUser(user);

		when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

		groupService.unAssignUserFromGroup(groupId, userId);

		verify(groupRepository).save(group);
		verify(groupRepository).findById(groupId);
		assertEquals(0, group.getUsers().size());
	}
}