package com.educational.portal;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.repository.RoleRepository;
import com.educational.portal.repository.UserRepository;
import com.educational.portal.util.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EducationalPortalApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public EducationalPortalApplication(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(EducationalPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// for testing we create 4 basic roles and 4 users
		var adminRole = new Role(Constants.ADMIN_ROLE);
		var managerRole = new Role(Constants.MANAGER_ROLE);
		var instructorRole = new Role(Constants.INSTRUCTOR_ROLE);
		var userRole = new Role(Constants.USER_ROLE);

		adminRole = roleRepository.save(adminRole);
		managerRole = roleRepository.save(managerRole);
		instructorRole = roleRepository.save(instructorRole);
		userRole = roleRepository.save(userRole);

		var user1 = new User("firstName1", "lastName1", passwordEncoder.encode("password1"), "email1@gmail.com", "+380686930810", adminRole);
		var user2 = new User("firstName2", "lastName2", passwordEncoder.encode("password2"), "email2@gmail.com", "+380686930820", managerRole);
		var user3 = new User("firstName3", "lastName3", passwordEncoder.encode("password3"), "email3@gmail.com", "+380686930830", instructorRole);
		var user4 = new User("firstName4", "lastName4", passwordEncoder.encode("password4"), "email4@gmail.com", "+380686930840", userRole);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
	}
}