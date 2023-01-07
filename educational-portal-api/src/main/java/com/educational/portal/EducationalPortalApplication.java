package com.educational.portal;

import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.repository.CategoryRepository;
import com.educational.portal.repository.GroupRepository;
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
	private final CategoryRepository categoryRepository;
	private final GroupRepository groupRepository;

	public EducationalPortalApplication(UserRepository userRepository,
										RoleRepository roleRepository,
										PasswordEncoder passwordEncoder,
										CategoryRepository categoryRepository, GroupRepository groupRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.categoryRepository = categoryRepository;
		this.groupRepository = groupRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EducationalPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addUsersAndRoles();
		addCategories();
		addGroup();
	}

	private void addUsersAndRoles() {
		// for testing we create 4 basic roles and 4 users
		var adminRole = new Role(Constants.ADMIN_ROLE);
		var managerRole = new Role(Constants.MANAGER_ROLE);
		var instructorRole = new Role(Constants.INSTRUCTOR_ROLE);
		var userRole = new Role(Constants.USER_ROLE);

		adminRole = roleRepository.save(adminRole);
		managerRole = roleRepository.save(managerRole);
		instructorRole = roleRepository.save(instructorRole);
		userRole = roleRepository.save(userRole);

		var user1 = new User("firstName1", "lastName1", passwordEncoder.encode("admin"), "admin@gmail.com", "+380686930810", adminRole, true);
		var user2 = new User("firstName2", "lastName2", passwordEncoder.encode("manager"), "manager@gmail.com", "+380686930820", managerRole, true);
		var user3 = new User("firstName3", "lastName3", passwordEncoder.encode("instructor"), "instructor@gmail.com", "+380686930830", instructorRole, true);
		var user4 = new User("firstName4", "lastName4", passwordEncoder.encode("user"), "user@gmail.com", "+380686930840", userRole);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
	}

	private void addCategories() {
		User user = userRepository.findByEmail("admin@gmail.com").get();
		// for testing we create categories and sub categories
		Category category1 = new Category("Software", user);
		Category subCategory1 = new Category("Development", category1, user);
		Category subCategory2 = new Category("UI Design", category1, user);
		Category subCategory3 = new Category("Testing", category1, user);

		categoryRepository.save(category1);
		categoryRepository.save(subCategory1);
		categoryRepository.save(subCategory2);
		categoryRepository.save(subCategory3);

		Category category2 = new Category("Assembly");
		Category subCategory4 = new Category("TV Assembly", category2, user);
		Category subCategory5 = new Category("Monitor Pipeline", subCategory4, user);
		Category subCategory6 = new Category("General Pipeline", subCategory4, user);

		categoryRepository.save(category2);
		categoryRepository.save(subCategory4);
		categoryRepository.save(subCategory5);
		categoryRepository.save(subCategory6);
	}

	private void addGroup() {
		User userManager = userRepository.findByEmail("manager@gmail.com").get();
		User userInstroctor = userRepository.findByEmail("instructor@gmail.com").get();
		Category category = categoryRepository.findCategoryByName("Assembly").get();

		Group group = new Group("Hello", userManager, category, userInstroctor);
		group.addUser(userManager);
		groupRepository.save(group);
	}
}