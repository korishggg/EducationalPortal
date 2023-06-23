package com.educational.portal.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_messages")
public class Message extends BaseEntity {

	private String content;

	@ManyToOne
	@JoinColumn(name = "created_by_id")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	private Date timeStamp;

	public Message() {
	}

	public Message(String content, User createdBy, Group group, Date timeStamp) {
		this.content = content;
		this.createdBy = createdBy;
		this.group = group;
		this.timeStamp = timeStamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
