package com.educational.portal.repository;

import com.educational.portal.domain.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	Optional<Resource> findResourceByFileName(String fileName);
	@Query(value = "SELECT * FROM resource WHERE user_id = :userId LIMIT 1", nativeQuery = true)
	Optional<Resource> findResourceByUser(Long userId);
	@Query(value = "SELECT * FROM resource WHERE user_id = :userId", nativeQuery = true)
	List<Resource> findAllByUser(Long userId);
}
