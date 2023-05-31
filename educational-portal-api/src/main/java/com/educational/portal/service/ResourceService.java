package com.educational.portal.service;

import com.educational.portal.domain.dto.ResourceDto;
import com.educational.portal.domain.entity.Resource;
import com.educational.portal.exception.NotFoundException;
import com.educational.portal.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
	private final ResourceRepository resourceRepository;
	private final StorageService storageService;

	ResourceService(ResourceRepository resourceRepository, StorageService storageService) {
		this.resourceRepository = resourceRepository;
		this.storageService = storageService;
	}


	public List<ResourceDto> getAllResourcesForUser(Long userId) {

		return resourceRepository.findAllByUser(userId)
				.stream()
				.map(ResourceDto::convertResourceToResourceDto)
				.toList();
	}

	public byte[] getFileByResourceId(Long resourceId) {
		Resource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new NotFoundException("Resource with id " + resourceId + " is not found"));
		return this.storageService.downloadFile(resource.getFileName());
	}

}
