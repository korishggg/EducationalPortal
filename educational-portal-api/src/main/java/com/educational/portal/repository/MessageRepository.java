package com.educational.portal.repository;

import com.educational.portal.domain.entity.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
	List<Message> findAllByGroupId(Long groupId, Pageable pageable);
}
