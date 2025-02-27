package com.educational.portal.domain.entity;

import com.educational.portal.validation.ContactNumberConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity {

	@NotBlank(message = "firstName is mandatory")
	private String firstName;
	@NotBlank(message = "lastName is mandatory")
	private String lastName;
	@NotBlank(message = "password is mandatory")
	private String password;
	@Email
	private String email;
	@ContactNumberConstraint
	private String phone;
	@Column(name = "isApproved")
	private boolean isApproved;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@ManyToMany(mappedBy = "users")
	List<Group> groups = new ArrayList<>();

	@Column(name = "IBAN")
	private String iban;

	@JsonIgnore
	@OneToMany(mappedBy = "createdBy")
	private List<Message> messages;

	public User() {
		super();
	}

	public User(String firstName, String lastName, List<Message> messages) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.messages = messages;
	}

	public User(String firstName, String lastName, String password, String email, String phone, Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}


	public User(String firstName, String lastName, String password, String email, String phone, Role role, boolean isApproved) {
		this(firstName, lastName, password, email, phone, role);
		this.isApproved = isApproved;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void setApproved(boolean approved) {
		isApproved = approved;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
