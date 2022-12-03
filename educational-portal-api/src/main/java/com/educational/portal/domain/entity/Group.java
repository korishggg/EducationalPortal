package com.educational.portal.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "educational_group")
public class Group extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;
	@ManyToOne
	private Category category;

	@ManyToOne
	private User creator;

	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<>();

	@ManyToOne
	private User instructor;

	public Group(List<User> users) {
		this.users = users;
	}

	public Group(String name, User creator, Category category, User instructor) {
		this.name = name;
		this.category = category;
		this.creator = creator;
		this.instructor = instructor;
	}

	public Group() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getInstructor() {
		return instructor;
	}

	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

}