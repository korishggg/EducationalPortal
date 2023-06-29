package com.educational.portal.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MessageRequestDto {
	private Long groupId;
	private Long userId;
	private String message;
}
