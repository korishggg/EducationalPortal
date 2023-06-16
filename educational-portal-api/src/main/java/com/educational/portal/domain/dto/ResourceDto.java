package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Resource DTO", description = "DTO class for a resource")
public class ResourceDto {
	@Schema(description = "The ID of the resource")
	private Long id;

	@Schema(description = "The type of the resource")
	private String type;

	public static ResourceDto convertResourceToResourceDto(Resource resource) {
		return new ResourceDto(resource.getId(), resource.getType().toString());
	}
}
