package com.educational.portal.web;

import com.educational.portal.service.ResourceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/resources")
public class ResourceController {
	ResourceService resourceService;

	ResourceController(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@GetMapping("/getResource/{fileId}")
	public ResponseEntity<ByteArrayResource> getFileByResourceId(@PathVariable Long fileId) {
		byte[] data = resourceService.getFileByResourceId(fileId);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octec-stream")
				.header("Content-disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
