package com.educational.portal.web;

import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Manager", description = "Manager operations")
@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get Unapproved Users",
            description = "Retrieves a list of unapproved users.",
            tags = {"manager", "unapprovedUsers"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/unapprovedUsers")
    public ResponseEntity<List<UserDto>> getUnapprovedUsers() {
        List<UserDto> unapprovedUsers = userService.getUnapprovedUsers();
        return ResponseEntity.ok(unapprovedUsers);
    }

    @Operation(
            summary = "Get Approved Users",
            description = "Retrieves a list of approved users.",
            tags = {"manager", "approvedUsers"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/approvedUsers")
    public ResponseEntity<List<UserDto>> getApprovedUsers() {
        List<UserDto> approvedUsers = userService.getApprovedUsers();
        return ResponseEntity.ok(approvedUsers);
    }

    @Operation(
            summary = "Approve User",
            description = "Approves a user by their id.",
            tags = {"manager", "approveUser"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success: User approved"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/approveUser/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        userService.approveUserById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Assign Instructor",
            description = "Assigns an instructor role to a user by their id.",
            tags = {"manager", "assignInstructor"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success: Instructor assigned"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/assignInstructor/{id}")
    public ResponseEntity<?> assignInstructor(@PathVariable Long id) {
        userService.assignInstructorByUserId(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get All Instructors",
            description = "Retrieves a list of all instructors.",
            tags = {"manager", "instructors"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/instructors")
    public ResponseEntity<List<UserDto>> getAllInstructors() {
        List<UserDto> instructors = userService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

}
