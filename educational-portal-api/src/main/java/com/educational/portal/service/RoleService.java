package com.educational.portal.service;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role getRoleByName(String roleName) {
		return roleRepository.findByName(roleName)
							 .orElseThrow(() -> new RuntimeException("Role with this name= " + roleName +" is not found"));
	}
}
