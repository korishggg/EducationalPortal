package com.educational.portal.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "created")
	private LocalDateTime created;

	@Column(name = "updated")
	private LocalDateTime updated;

	@Column(name = "isDeleted")
	private Boolean isDeleted;

	@Column(name = "isDisabled")
	private Boolean isDisabled;

	public BaseEntity() {
		this.created = LocalDateTime.now();
		this.updated = LocalDateTime.now();
		this.isDeleted = false;
		this.isDisabled = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean disabled) {
		isDisabled = disabled;
	}
}
