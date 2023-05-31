package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {

	private Long id;
	private String type;

	public static ResourceDto convertResourceToResourceDto(Resource resource) {
		return new ResourceDto(resource.getId(), resource.getType().toString());
	}

}
