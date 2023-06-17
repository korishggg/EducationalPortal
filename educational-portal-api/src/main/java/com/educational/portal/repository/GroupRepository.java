package com.educational.portal.repository;

import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findGroupByName(String name);
	List<Group> findAllByUsersContaining(User user);
	List<Group> findAllByInstructor(User instructor);
}