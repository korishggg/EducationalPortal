package com.educational.portal.service;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.repository.RoleRepository;
import com.educational.portal.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
	@Mock
	private RoleRepository roleRepository;

	private RoleService roleService;

	@Test
	void getRoleByName() {
		roleService = new RoleService(roleRepository);
		Role role = new Role(Constants.USER_ROLE);

		when(roleRepository.findByName(Constants.USER_ROLE)).thenReturn(Optional.of(role));

		Role returnedRole = roleService.getRoleByName(Constants.USER_ROLE);

		assertEquals(returnedRole, role);
	}
}