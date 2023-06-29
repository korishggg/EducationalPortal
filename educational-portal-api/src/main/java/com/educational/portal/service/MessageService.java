package com.educational.portal.service;

import com.educational.portal.messaging.dto.MessageResponseDto;
import com.educational.portal.domain.entity.Group;
import com.educational.portal.domain.entity.Message;
import com.educational.portal.domain.entity.User;
import com.educational.portal.exception.NotAllowedOperationException;
import com.educational.portal.messaging.dto.MessageRequestDto;
import com.educational.portal.repository.MessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {
	private final MessageRepository messageRepository;
	private final GroupService groupService;
	private final UserService userService;
	private final SimpMessagingTemplate messagingTemplate;

	public MessageService(MessageRepository messageRepository, GroupService groupService, UserService userService, SimpMessagingTemplate messagingTemplate) {
		this.messageRepository = messageRepository;
		this.groupService = groupService;
		this.userService = userService;
		this.messagingTemplate = messagingTemplate;
	}

	public MessageResponseDto sendMessage(MessageRequestDto groupMessageDto) {
		Date date = new Date();
		User user = userService.findUserById(groupMessageDto.getUserId());
		Group group = groupService.findById(groupMessageDto.getGroupId());
		Message message = new Message(groupMessageDto.getMessage(), user, group, date);
		if (!message.getContent().isEmpty()) {
			messageRepository.save(message);
			MessageResponseDto messageDto = MessageResponseDto.convertMessageToMessageDto(message);
//			messagingTemplate.convertAndSend("/topic/groupMessages", messageDto);
			return messageDto;
		} else {
			throw new NotAllowedOperationException("The message is empty");
		}
	}

	public List<MessageResponseDto> findAllLimitPageForCurrentGroup(Long groupId, int page, int pageSize) {
		Pageable firstPageWithTenElements = PageRequest.of(page, pageSize);
		return messageRepository.findAllByGroupId(groupId, firstPageWithTenElements)
				.stream()
				.map(MessageResponseDto::convertMessageToMessageDto)
				.toList();
	}
}
