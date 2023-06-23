package com.educational.portal.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@JsonIgnore
	private User creator;

	@ManyToMany
	@JoinTable(name = "user_educational_group",
			joinColumns = {
					@JoinColumn(name = "group_id", referencedColumnName = "id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id")
			}
	)
	private List<User> users = new ArrayList<>();

	@ManyToOne
	private User instructor;

	@JsonIgnore
	@OneToMany(mappedBy = "group")
	private List<Message> messages;

	public Group(String name, List<User> users) {
		this.name = name;
		this.users = users;
	}

	public Group(List<Message> messages) {
		this.messages = messages;
	}

	public Group(String name, User creator, Category category, User instructor) {
		this.name = name;
		this.category = category;
		this.creator = creator;
		this.instructor = instructor;
	}

	public Group() {
	}

	public Group(String name, User userManager, Category category) {
		this.name = name;
		this.category = category;
		this.creator = userManager;
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

	public void removeUser(User user) {
		this.users.remove(user);
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}