package com.educational.portal.repository;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	List<User> findUsersByIsApprovedAndRole(boolean isApproved, Role role);
//	TODO REPLACE THIS METHOD TO THIS in ALL PLACES findUsersByIsApprovedAndRole
	List<User> findUsersByIsApprovedAndRoleName(boolean isApproved,String roleName);

}
