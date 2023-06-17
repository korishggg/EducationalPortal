package com.educational.portal.web;

import com.educational.portal.service.ResourceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/resources")
@Tag(name = "Resources", description = "Resource operations")
public class ResourceController {
	private final ResourceService resourceService;

	public ResourceController(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Operation(
			summary = "Get File by ID",
			description = "Retrieves a file by its ID.",
			tags = {"resources", "file"})
	@ApiResponse(
			responseCode = "200",
			description = "Success: File found",
			content = {@Content(mediaType = "application/octet-stream")})
	@ApiResponse(
			responseCode = "404",
			description = "File not found")
	@GetMapping("/{id}/file")
	public ResponseEntity<ByteArrayResource> getFileById(
			@Parameter(description = "ID of the file to be retrieved")
			@PathVariable Long id) {
		byte[] data = resourceService.getFileById(id);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
