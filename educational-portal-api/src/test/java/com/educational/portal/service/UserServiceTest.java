package com.educational.portal.service;

import com.educational.portal.TestConstants;
import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.AuthRequest;
import com.educational.portal.domain.dto.AuthResponse;
import com.educational.portal.domain.dto.RegistrationRequest;
import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.IncorrectPasswordException;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.exception.NotEnoughPermissionException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.UserRepository;
import com.educational.portal.security.JwtProvider;
import com.educational.portal.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private RoleService roleService;
	@Mock
	private JwtProvider jwtProvider;
	private UserService userService;

	Long userId = 1L;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, passwordEncoder, roleService, jwtProvider);
	}

	@Test
	void findByEmail() {
		var email = TestConstants.USER_WITH_USER_ROLE.getEmail();

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));

		User returnedUser = userService.findByEmail(email);

		assertEquals(returnedUser, TestConstants.USER_WITH_USER_ROLE);
	}

	@Test
	void findByEmailWhenUserNotFound() {
		var email = "email";

		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		Throwable userNotFoundException = assertThrows(NotFoundException.class, () -> userService.findByEmail(email));

		assertEquals("User with this email " + email + " is not found", userNotFoundException.getMessage());
	}

	@Test
	void findUserById() {
		when(userRepository.findById(userId)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));

		User returnedUser = userService.findUserById(userId);

		assertEquals(returnedUser, TestConstants.USER_WITH_USER_ROLE);
	}

	@Test
	void findUserByIdWhenUserNotFound() {
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		Throwable userNotFoundException = assertThrows(NotFoundException.class, () -> userService.findUserById(userId));

		assertEquals("User with this id " + userId + " is not found", userNotFoundException.getMessage());
	}

	@Test
	void assignInstructorByUserId() {
		User user = new User("firstName", "lastName", "password", "email", "phone", TestConstants.USER_ROLE);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(roleService.getRoleByName(Constants.INSTRUCTOR_ROLE)).thenReturn(TestConstants.INSTRUCTOR_ROLE);

		userService.assignInstructorByUserId(userId);

		verify(userRepository).save(user);
	}

	@Test
	void assignInstructorByUserIdWhenRoleIsNotUserRole() {
		when(userRepository.findById(userId)).thenReturn(Optional.of(TestConstants.USER_WITH_ADMIN_ROLE));

		userService.assignInstructorByUserId(userId);

		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void signIn() {
		var email = "email";
		var password = "password";
		var token = "token";
		var refreshToken = "refreshToken";

		AuthRequest authRequest = new AuthRequest(email, password);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));
		when(passwordEncoder.matches(password, password)).thenReturn(true);
		when(jwtProvider.generateToken(email)).thenReturn(token);
		when(jwtProvider.refreshToken(email)).thenReturn(refreshToken);

		AuthResponse response = userService.signIn(authRequest);

		assertEquals(response.getToken(), token);
		assertEquals(response.getRefreshToken(), refreshToken);
	}

	@Test
	void signInWhenPasswordIsNotMatched() {
		var email = "email";
		var incorrectPassword = "password1";
		var password = "password";

		AuthRequest authRequest = new AuthRequest(email, incorrectPassword);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));
		when(passwordEncoder.matches(incorrectPassword, password)).thenReturn(false);

		Throwable incorrectPasswordException = assertThrows(IncorrectPasswordException.class, () -> userService.signIn(authRequest));

		assertEquals("User with this email " + authRequest.getEmail() + " typed incorrect password", incorrectPasswordException.getMessage());
	}

	@Test
	void registerUserWhenEmailIsExist() {
		var email = "email";

		RegistrationRequest registrationRequest = new RegistrationRequest("firstName", "lastName", "password", email, "+3876754455");

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> userService.registerUser(registrationRequest));

		assertEquals("User with this email " + email + " already exists", alreadyExistsException.getMessage());
	}

	@Test
	void registerUser() {
		var email = "email";
		var password = "password";
		var encodedPassword = "encodedPassword";

		RegistrationRequest registrationRequest = new RegistrationRequest("firstName", "lastName", password, email, "+3876754455");

		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		when(roleService.getRoleByName(Constants.USER_ROLE)).thenReturn(TestConstants.USER_ROLE);
		when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

		userService.registerUser(registrationRequest);

		verify(userRepository).save(any(User.class));
	}

	@Test
	void getUnapprovedUsers() {
		when(roleService.getRoleByName(Constants.USER_ROLE)).thenReturn(TestConstants.USER_ROLE);
		when(userRepository.findUsersByIsApprovedAndRole(false, TestConstants.USER_ROLE)).thenReturn(List.of(new User()));

		List<UserDto> returnedUsers = userService.getUnapprovedUsers();

		verify(userRepository).findUsersByIsApprovedAndRole(false, TestConstants.USER_ROLE);
		verify(roleService).getRoleByName(Constants.USER_ROLE);

		assertEquals(returnedUsers.size(), 1);
	}

	@Test
	void approveUserById() {
		when(userRepository.findById(userId)).thenReturn(Optional.of(TestConstants.USER_WITH_USER_ROLE));

		userService.approveUserById(userId);

		verify(userRepository).save(any(User.class));
	}

	@Test
	void assignManagerByUserId() {
		var user = new User("firstName", "lastName", "password", "email", "phone", TestConstants.USER_ROLE);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(roleService.getRoleByName(Constants.MANAGER_ROLE)).thenReturn(TestConstants.MANAGER_ROLE);

		userService.assignManagerByUserId(userId);

		verify(userRepository).save(any(User.class));

		assertEquals(user.getRole(), TestConstants.MANAGER_ROLE);
	}

	@Test
	void assignManagerByUserIdWhenHaveAdminRole() {
		when(userRepository.findById(userId)).thenReturn(Optional.of(TestConstants.USER_WITH_ADMIN_ROLE));

		Throwable notEnoughPermissionException = assertThrows(NotEnoughPermissionException.class, () -> userService.assignManagerByUserId(userId));
		assertEquals("Current user cannot assign another administrator to the manager role", notEnoughPermissionException.getMessage());

		verify(userRepository).findById(userId);
	}

	@Test
	void addUserBankAccount() {
		Principal principal = mock(Principal.class);

		User user = new User("firstName", "lastName", "password", "email", "phone", new Role(Constants.ADMIN_ROLE));
		user.setIsApproved(true);

		AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest("bank");

		when(principal.getName()).thenReturn(user.getEmail());
		when(userRepository.findByEmail(principal.getName())).thenReturn(Optional.of(user));

		userService.addUserBankAccount(principal, addBankAccountRequest);

		verify(userRepository).save(any(User.class));
	}

	@Test
	void addUserBankAccountWhenApprovedFalse() {
		Principal principal = mock(Principal.class);

		User user = new User("firstName", "lastName", "password", "email", "phone", new Role(Constants.ADMIN_ROLE));
		user.setId(userId);
		user.setIsApproved(false);

		AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest("bank");

		when(userRepository.findByEmail(principal.getName())).thenReturn(Optional.of(user));

		Throwable notAllowedOperationException = assertThrows(NotAllowedOperationException.class, () -> userService.addUserBankAccount(principal, addBankAccountRequest));

		assertEquals("User with this id " + userId + " is not approved", notAllowedOperationException.getMessage());
	}

}