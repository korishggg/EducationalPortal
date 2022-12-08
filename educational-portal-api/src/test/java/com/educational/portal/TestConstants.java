package com.educational.portal;

import com.educational.portal.domain.entity.Category;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import com.educational.portal.util.Constants;

public class TestConstants {
	public static final Role MANAGER_ROLE = new Role(Constants.MANAGER_ROLE);
	public static final Role USER_ROLE = new Role(Constants.USER_ROLE);
	public static final Role ADMIN_ROLE = new Role(Constants.ADMIN_ROLE);
	public static final Role INSTRUCTOR_ROLE = new Role(Constants.INSTRUCTOR_ROLE);

	public static final User USER_WITH_USER_ROLE = new User("firstName", "lastName", "password", "email", "phone", TestConstants.USER_ROLE);
	public static final User USER_WITH_MANAGER_ROLE = new User("firstName", "lastName", "password", "email", "phone", TestConstants.MANAGER_ROLE);
	public static final User USER_WITH_ADMIN_ROLE = new User("firstName", "lastName", "password", "email", "phone", TestConstants.ADMIN_ROLE);
	public static final User USER_WITH_INSTRUCTOR_ROLE = new User("firstName", "lastName", "password", "email", "phone", TestConstants.INSTRUCTOR_ROLE);

	public static final Category CATEGORY = new Category("name");

	public static final Group GROUP_WITH_INSTRUCTOR = new Group("name", USER_WITH_MANAGER_ROLE, CATEGORY, USER_WITH_INSTRUCTOR_ROLE);
	public static final Group GROUP_WITHOUT_INSTRUCTOR = new Group("name", USER_WITH_MANAGER_ROLE, CATEGORY);
}