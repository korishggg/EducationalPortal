package com.educational.portal.repository;

import com.educational.portal.domain.entity.Role;
import com.educational.portal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	List<User> findUsersByIsApprovedAndRole(boolean isApproved, Role role);
	//	TODO REPLACE THIS METHOD TO THIS in ALL PLACES findUsersByIsApprovedAndRole
	List<User> findUsersByIsApprovedAndRoleName(boolean isApproved, String roleName);

	@Query(nativeQuery = true,
			value = "SELECT * FROM app_user u" +
					" INNER JOIN role r ON r.id = u.role_id" +
					" WHERE (UPPER(u.email) LIKE UPPER(?1) OR UPPER(u.last_name) LIKE UPPER(?2))" +
					" AND u.is_approved = ?3" +
					" AND r.name = ?4" +
					" AND NOT EXISTS (SELECT 1 FROM user_educational_group g WHERE g.user_id = u.id AND g.group_id = ?5)")
	List<User> findUsersByEmailLikeOrLastNameAndIsApprovedAndRoleNameAndNotInGroupId(
			String email,
			String lastName,
			boolean isApproved,
			String roleName,
			Long groupId);

}
