package com.educational.portal.service;

import com.educational.portal.TestConstants;
import com.educational.portal.domain.dto.CategoryDto;
import com.educational.portal.domain.dto.CreateCategoryRequest;
import com.educational.portal.domain.entity.Category;
import com.educational.portal.exception.AlreadyExistsException;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private UserService userService;
	private CategoryService categoryService;

	Long categoryId = 1L;

	@BeforeEach
	void setUp() {
		categoryService = new CategoryService(categoryRepository, userService);
	}

	@Test
	void getAllCategoriesWhereIsHideSubCategories() {
		boolean isHideSubCategories = true;
		List<Category> categories = new ArrayList<>();
		categories.add(new Category("test1", null, null));
		categories.add(new Category("test12", null, null));
		when(categoryRepository.findCategoriesByParentIsNull()).thenReturn(categories);

		List<CategoryDto> result = categoryService.getAllCategories(isHideSubCategories);

		verify(categoryRepository).findCategoriesByParentIsNull();
		assertEquals(2, result.size());
		for (CategoryDto categoryDto : result) {
			assertEquals(new ArrayList<>(), categoryDto.getSubcategories());
		}

	}

	@Test
	void getAllCategoriesWhereNotHideSubCategories() {
		boolean isHideSubCategories = false;
		List<Category> categories = new ArrayList<>();
		categories.add(new Category("test1", null, null));
		categories.add(new Category("test2", null, null));
		when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDto> expected = new ArrayList<>();
		Set<Long> processedCategoryIds = new HashSet<>();

		for (Category category : categories) {
			if (!processedCategoryIds.contains(category.getId())) {
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setName(category.getName());
				categoryDto.setSubcategories(new ArrayList<>());
				expected.add(categoryDto);
				processedCategoryIds.add(category.getId());
			}
		}

		List<CategoryDto> result = categoryService.getAllCategories(isHideSubCategories);

		verify(categoryRepository).findAll();
		assertEquals(expected.size(), result.size());
	}

	@Test
	void findByIdAndConvertToDtoWhenHideSubCategories() {
		Category category = new Category("test");
		Category subCategory = new Category("test1");
		category.addSubCategory(subCategory);
		boolean isHideSubCategories = true;

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

		CategoryDto result = categoryService.findByIdAndConvertToDto(categoryId, isHideSubCategories);

		verify(categoryRepository).findById(categoryId);
		assertEquals(result.getSubcategories().size(), 0);
	}

	@Test
	void findByIdAndConvertToDto() {
		Category category = new Category("test");
		Category subCategory = new Category("test1");
		category.addSubCategory(subCategory);

		boolean isHideSubCategories = false;

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

		CategoryDto result = categoryService.findByIdAndConvertToDto(categoryId, isHideSubCategories);

		verify(categoryRepository).findById(categoryId);
		assertEquals(result.getSubcategories().size(), 1);
	}

	@Test
	void createCategoryWhenNameIsExist() {
		Principal principal = mock(Principal.class);

		var name = "name";

		CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest(name);

		when(categoryRepository.findCategoryByName(name)).thenReturn(Optional.of(TestConstants.CATEGORY));

		Throwable alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> categoryService.createCategory(principal, createCategoryRequest));

		assertEquals("Category With this name " + name + " already exists", alreadyExistsException.getMessage());
	}

	@Test
	void createCategory() {
		Principal principal = mock(Principal.class);

		CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("name");

		when(principal.getName()).thenReturn(TestConstants.USER_WITH_USER_ROLE.getEmail());
		when(userService.findByEmail(TestConstants.USER_WITH_USER_ROLE.getEmail())).thenReturn(TestConstants.USER_WITH_USER_ROLE);

		categoryService.createCategory(principal, createCategoryRequest);

		verify(userService).findByEmail(TestConstants.USER_WITH_USER_ROLE.getEmail());
		verify(categoryRepository).save(any(Category.class));
	}

	@Test
	void assignSubcategoryToCategory() {
		Category subCategory = new Category();
		var subCategoryId = 2L;

		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(TestConstants.CATEGORY));
		when(categoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

		categoryService.assignSubcategoryToCategory(categoryId, subCategoryId);

		verify(categoryRepository).save(any(Category.class));
	}

	@Test
	void findById() {
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(TestConstants.CATEGORY));

		Category returnedCategory = categoryService.findById(categoryId);

		assertEquals(returnedCategory, TestConstants.CATEGORY);
	}

	@Test
	void findByIdWhenCategoryNotFound() {
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		Throwable categoryNotFoundException = assertThrows(NotFoundException.class, () -> categoryService.findById(categoryId));

		assertEquals("Category with this id " + categoryId + " is not found", categoryNotFoundException.getMessage());
	}

	@Test
	void deleteCategoryById() {
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(TestConstants.CATEGORY));

		categoryService.deleteCategoryById(categoryId);

		verify(categoryRepository).delete(any(Category.class));
	}
}