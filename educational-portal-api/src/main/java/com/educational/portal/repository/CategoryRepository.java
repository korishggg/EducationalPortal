package com.educational.portal.repository;

import com.educational.portal.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findCategoryByName(String name);
	List<Category> findCategoriesByParentIsNull();
	List<Category> findCategoriesByParentIsNotNull();
}