package com.educational.portal.domain.dto;

import com.educational.portal.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "User DTO", description = "DTO class for a user")
public class UserDto {

    @Schema(description = "The ID of the user")
    private Long id;

    @Schema(description = "The first name of the user")
    private String firstName;

    @Schema(description = "The last name of the user")
    private String lastName;

    @Schema(description = "The email address of the user")
    private String email;

    @Schema(description = "The phone number of the user")
    private String phone;

    @Schema(description = "Flag indicating if the user is approved")
    private boolean isApproved;

    public static UserDto convertUserToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.isApproved()
        );
    }
}
