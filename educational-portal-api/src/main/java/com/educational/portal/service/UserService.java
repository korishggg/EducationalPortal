package com.educational.portal.service;

import com.educational.portal.domain.dto.*;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.IncorrectPasswordException;
import com.educational.portal.exception.NotEnoughPermissionException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.UserRepository;
import com.educational.portal.security.JwtProvider;
import com.educational.portal.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	private final JwtProvider jwtProvider;

	public UserService(UserRepository userRepository,
					   PasswordEncoder passwordEncoder,
					   RoleService roleService,
					   JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.jwtProvider = jwtProvider;
	}

	public AuthResponse signIn(AuthRequest authRequest) {
		User user = findByEmail(authRequest.getEmail());
		boolean isPasswordMatched = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
		if (!isPasswordMatched) {
			throw new IncorrectPasswordException("User with this email " + authRequest.getEmail() +" typed incorrect password");
		}

		String token = jwtProvider.generateToken(user.getEmail());
		String refreshToken = jwtProvider.refreshToken(user.getEmail());
		return new AuthResponse(token, refreshToken);
	}

	public void registerUser(RegistrationRequest registrationRequest) {
		validateIsUserExists(registrationRequest.getEmail());

		Role userRole = roleService.getRoleByName(Constants.USER_ROLE);
		User user = RegistrationRequest.convertRegistrationRequestToUser(registrationRequest, userRole, passwordEncoder);

		userRepository.save(user);
		LOGGER.info("user with this email" + user.getEmail() +" is saved");
	}

	private void validateIsUserExists(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new AlreadyExistsException("User with this email " + email + " already exists");
		}
	}

	public List<UserDto> getUnapprovedUsers() {
		return userRepository.findUsersByIsApproved(false)
							 .stream()
							 .map(UserDto::convertUserToUserDto)
							 .toList();
	}

	public void approveUserById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			var user = userOptional.get();
			user.setIsApproved(true);
			userRepository.save(user);
			LOGGER.info("User with this id " + id + " is approved");
		} else {
			throw new NotFoundException("User with this id " + id + " is not found");
		}
	}

	public void assignManagerByUserId(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			var user = userOptional.get();
			if (!user.getRole().getName().equals(Constants.ADMIN_ROLE)) {
				var role = roleService.getRoleByName(Constants.MANAGER_ROLE);
				user.setRole(role);
				userRepository.save(user);
				LOGGER.info("User with this id " + id + " been assigned with " + Constants.MANAGER_ROLE + " role");
			} else {
				throw new NotEnoughPermissionException("Current user cannot assign another administrator to the manager role");
			}
		} else {
			throw new NotFoundException("User with this id " + id + " is not found");
		}
	}

	public void assignInstructorByUserId(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isPresent()) {
			var user = userOptional.get();
			if(user.getRole().getName().equals(Constants.USER_ROLE)) {
				var role = roleService.getRoleByName(Constants.INSTRUCTOR_ROLE);
				user.setRole(role);
				userRepository.save(user);
				LOGGER.info("User with this id " + id + "has been assigned with " + Constants.INSTRUCTOR_ROLE + " role");
			}else {
				throw new NotFoundException("User with this id " + id + " is not found or don't have User role");
			}
		}
	}

	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String refreshToken = refreshTokenRequest.getRefreshToken();
		boolean isTokenValid = jwtProvider.validateToken(refreshToken);
		if(isTokenValid) {
			String email = jwtProvider.getEmailFromToken(refreshToken);
			String authRefreshToken = jwtProvider.refreshToken(email);
			String authToken = jwtProvider.generateToken(email);
			return new AuthResponse(authToken,authRefreshToken);
		}
		throw new IncorrectPasswordException("This token is not valid");
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
							 .orElseThrow(() -> {
								 throw new NotFoundException("User with this email " + email + " is not found");
							 });
	}
}
