package com.educational.portal.service;

import com.educational.portal.domain.dto.AuthRequest;
import com.educational.portal.domain.dto.AuthResponse;
import com.educational.portal.domain.dto.RegistrationRequest;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.repository.RoleRepository;
import com.educational.portal.repository.UserRepository;
import com.educational.portal.security.JwtProvider;
import com.educational.portal.util.Constants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public UserService(UserRepository userRepository,
					   RoleRepository roleRepository,
					   PasswordEncoder passwordEncoder,
					   JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	}

	public AuthResponse signIn(AuthRequest authRequest) {
		User user = userRepository.findByEmail(authRequest.getEmail())
								  .orElseThrow(() -> {
									  throw new RuntimeException("User with this email " + authRequest.getEmail() +" is not found");
								  });
		boolean isPasswordMatched = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
		if (!isPasswordMatched) {
			throw new RuntimeException("User with this email " + authRequest.getEmail() +" typed incorrect password");
		}

		String token = jwtProvider.generateToken(user.getEmail());
		return new AuthResponse(token);
	}

	public void registerUser(RegistrationRequest registrationRequest) {
		validateIsUserExists(registrationRequest.getEmail());

		Role userRole = roleRepository.findByName(Constants.USER_ROLE)
									  .orElseThrow(() -> new RuntimeException(Constants.USER_ROLE + "IS NOT FOUND"));
		// TODO add mapper
		User user = new User(registrationRequest.getFirstName(),
							 registrationRequest.getLastName(),
							 passwordEncoder.encode(registrationRequest.getPassword()),
							 registrationRequest.getEmail(),
							 registrationRequest.getPhone(),
							 userRole);
		userRepository.save(user);
		System.out.println("user with this email" + user.getEmail() +" is saved");
	}

	private void validateIsUserExists(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new RuntimeException("User with this email " + email + " already exists");
		}
	}

	public List<?> getUnapprovedUsers() {
		return userRepository.findUsersByIsApproved(false);
	}

	public User approveUserById(Long id) {
		return userRepository.findById(id)
							 .map(user -> {
								 user.setIsApproved(true);
								 return userRepository.save(user);
							 })
							 .orElseThrow(() -> new RuntimeException("User with this id " + id + " is not found"));
	}
}
