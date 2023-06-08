package com.educational.portal.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Resource extends BaseEntity {

	public enum ResourceType {
		TAX_ID("TAX_ID"),
		PASSPORT("PASSPORT");

		private final String value;

		ResourceType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	@Column(name = "file_name", unique = true)
	private String fileName;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ResourceType type;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Resource(String fileName, ResourceType type, User user) {
		this.fileName = fileName;
		this.type = type;
		this.user = user;
	}

	public Resource() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
