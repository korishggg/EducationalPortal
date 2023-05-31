package com.educational.portal.service;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.AuthRequest;
import com.educational.portal.domain.dto.AuthResponse;
import com.educational.portal.domain.dto.RefreshTokenRequest;
import com.educational.portal.domain.dto.RegistrationRequest;
import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.domain.dto.UserInfoDto;
import com.educational.portal.domain.entity.Resource;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.*;
import com.educational.portal.repository.ResourceRepository;
import com.educational.portal.repository.UserRepository;
import com.educational.portal.security.JwtProvider;
import com.educational.portal.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	private final JwtProvider jwtProvider;
	private final StorageService storageService;
	private final ResourceRepository resourceRepository;

	public UserService(UserRepository userRepository,
					   PasswordEncoder passwordEncoder,
					   RoleService roleService,
					   JwtProvider jwtProvider,
					   StorageService storageService,
					   ResourceRepository resourceRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.jwtProvider = jwtProvider;
		this.storageService = storageService;
		this.resourceRepository = resourceRepository;
	}

	public AuthResponse signIn(AuthRequest authRequest) {
		User user = findByEmail(authRequest.getEmail());
		boolean isPasswordMatched = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
		if (!isPasswordMatched) {
			throw new IncorrectPasswordException("User with this email " + authRequest.getEmail() + " typed incorrect password");
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
		LOGGER.info("user with this email" + user.getEmail() + " is saved");
	}

	private void validateIsUserExists(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			throw new AlreadyExistsException("User with this email " + email + " already exists");
		}
	}

	public List<UserDto> getUnapprovedUsers() {
		Role role = roleService.getRoleByName(Constants.USER_ROLE);
		return userRepository.findUsersByIsApprovedAndRole(false, role)
				.stream()
				.map(UserDto::convertUserToUserDto)
				.toList();
	}

	public List<UserDto> getApprovedUsers() {
		Role role = roleService.getRoleByName(Constants.USER_ROLE);
		return userRepository.findUsersByIsApprovedAndRole(true, role)
				.stream()
				.map(UserDto::convertUserToUserDto)
				.toList();
	}

	public void approveUserById(Long id) {
		User user = findUserById(id);
		user.setIsApproved(true);
		userRepository.save(user);
		LOGGER.info("User with this id " + id + " is approved");
	}

	public void assignManagerByUserId(Long id) {
		User user = findUserById(id);
		if (!user.getRole().getName().equals(Constants.ADMIN_ROLE)) {
			var role = roleService.getRoleByName(Constants.MANAGER_ROLE);
			user.setRole(role);
			userRepository.save(user);
			LOGGER.info("User with this id " + id + " been assigned with " + Constants.MANAGER_ROLE + " role");
		} else {
			throw new NotEnoughPermissionException("Current user cannot assign another administrator to the manager role");
		}
	}

	public void assignInstructorByUserId(Long id) {
		User user = findUserById(id);
		if (user.getRole().getName().equals(Constants.USER_ROLE)) {
			var role = roleService.getRoleByName(Constants.INSTRUCTOR_ROLE);
			user.setRole(role);
			userRepository.save(user);
			LOGGER.info("User with this id " + id + " has been assigned with " + Constants.INSTRUCTOR_ROLE + " role");
		}
	}

	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String refreshToken = refreshTokenRequest.getRefreshToken();
		boolean isTokenValid = jwtProvider.validateToken(refreshToken);
		if (isTokenValid) {
			String email = jwtProvider.getEmailFromToken(refreshToken);
			String authRefreshToken = jwtProvider.refreshToken(email);
			String authToken = jwtProvider.generateToken(email);
			return new AuthResponse(authToken, authRefreshToken);
		}
		throw new IncorrectPasswordException("This token is not valid");
	}

	public void addUserBankAccount(Principal principal, AddBankAccountRequest addBankAccountRequest) {
		User user = findByEmail(principal.getName());
		if (user.isApproved()) {
			user.setIban(addBankAccountRequest.getIban());
			userRepository.save(user);
			LOGGER.info("User bank account is added");
		} else {
			throw new NotAllowedOperationException("User with this id " + user.getId() + " is not approved");
		}
	}

	public User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User with this id " + id + " is not found"));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> {
					throw new NotFoundException("User with this email " + email + " is not found");
				});
	}

	public UserInfoDto getUserInfoForAuthorizedUser(Principal principal) {
		User user = findByEmail(principal.getName());
		return UserInfoDto.convertUserToUserDto(user);
	}

	public boolean isCurrentUserApproved(Principal principal) {
		User user = findByEmail(principal.getName());
		return user.isApproved();
	}

	public List<UserDto> getAllInstructors() {
		return userRepository.findUsersByIsApprovedAndRoleName(true, Constants.INSTRUCTOR_ROLE)
				.stream()
				.map(UserDto::convertUserToUserDto)
				.collect(Collectors.toList());
	}

	public void uploadData(Principal principal,
						   MultipartFile[] passportFiles,
						   MultipartFile[] taxIdFiles) {
		validateImagesUploading(taxIdFiles, passportFiles);
		User user = this.findByEmail(principal.getName());
		List<Resource> resources = new ArrayList<>();
		if (!user.isApproved()) {
			String passportFolder = "documents/" + user.getId() + "/passport/";
			String taxIdFolder = "documents/" + user.getId() + "/taxId/";
			for (MultipartFile file : taxIdFiles) {
				String filePath = this.storageService.uploadFile(file, taxIdFolder);
				Resource resource = new Resource(filePath, Resource.ResourceType.TAX_ID, user);
				resources.add(resource);
			}
			for (MultipartFile file : passportFiles) {
				String filePath = this.storageService.uploadFile(file, passportFolder);
				Resource resource = new Resource(filePath, Resource.ResourceType.PASSPORT, user);
				resources.add(resource);
			}
			resourceRepository.saveAll(resources);
		} else {
			throw new NotAllowedOperationException("Current user with id " + user.getId() + " is approved.");
		}
	}

	private void validateImagesUploading(MultipartFile[] taxIdFiles, MultipartFile[] passportFiles) {
		if (taxIdFiles == null || taxIdFiles.length == 0) {
			throw new RuntimeException("taxIdFiles is not specified");
		}
		if (passportFiles == null || passportFiles.length == 0) {
			throw new RuntimeException("passportFiles is not specified");
		}
		verifyIsAllImageFiles(taxIdFiles);
		verifyIsAllImageFiles(passportFiles);
	}


	private void verifyIsAllImageFiles(MultipartFile[] taxIdFiles) {
		for (MultipartFile file : taxIdFiles) {
			isImage(file);
			if (!isImage(file)) {
				throw new IllegalArgumentException("Invalid file format for passportFiles. Only JPG, JPEG, JFIF, PJPEG, PJP, and PNG images are allowed.");
			}
		}
	}

	private boolean isImage(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String fileExtension = getFileExtension(fileName);
		return fileExtension != null && (
				fileExtension.equalsIgnoreCase("jpg") ||
						fileExtension.equalsIgnoreCase("jpeg") ||
						fileExtension.equalsIgnoreCase("jfif") ||
						fileExtension.equalsIgnoreCase("pjpeg") ||
						fileExtension.equalsIgnoreCase("pjp") ||
						fileExtension.equalsIgnoreCase("png")
		);
	}

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1).toLowerCase();
		}
		return null;
	}

	public boolean isResourceExistsForUser(Principal principal) {
		Optional<User> user = userRepository.findByEmail(principal.getName());
		Optional<Resource> resource = resourceRepository.findResourceByUser(user.get().getId());
		return resource.isPresent();
	}
}
