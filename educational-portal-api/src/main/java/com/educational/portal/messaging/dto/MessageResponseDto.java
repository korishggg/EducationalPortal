package com.educational.portal.messaging.dto;

import com.educational.portal.domain.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
	private String content;
	private Long groupId;
	private Long userId;
	private String userFirstName;
	private String userLastName;

	public static MessageResponseDto convertMessageToMessageDto(Message message) {
		return new MessageResponseDto(
				message.getContent(),
				message.getGroup().getId(),
				message.getCreatedBy().getId(),
				message.getCreatedBy().getFirstName(),
				message.getCreatedBy().getLastName()
		);
	}
}
