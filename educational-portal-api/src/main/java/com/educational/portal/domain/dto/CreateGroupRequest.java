package com.educational.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Create Group Request", description = "DTO class for creating a group")
public class CreateGroupRequest {

    @Schema(description = "The name of the group", required = true, example = "Group A")
    @NotBlank(message = "Group name should not be empty")
    private String name;

    @Schema(description = "The ID of the category")
    private Long categoryId;

    @Schema(description = "The ID of the instructor")
    private Long instructorId;
}
