package com.educational.portal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@JsonIgnoreProperties("parent")
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Category> subCategories = new ArrayList<>();

	@ManyToOne
	private User createdBy = null;;

	@ManyToOne
	private User updatedBy = null;

	public Category() {
		super();
	}

	public Category(String name) {
		this();
		this.name = name;
	}

	public Category(String name, User createdBy) {
		this(name);
		this.createdBy = createdBy;
		this.updatedBy = createdBy;
	}

	public Category(String name, Category parentCategory, User createdBy) {
		this(name, createdBy);
		this.parent = parentCategory;
	}

	public void addSubCategory(Category category) {
		this.subCategories.add(category);
	}
}
