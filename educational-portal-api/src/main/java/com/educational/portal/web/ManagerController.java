package com.educational.portal.web;

import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/unapprovedUsers")
    public ResponseEntity<List<UserDto>> getUnapprovedUsers() {
        List<UserDto> unapprovedUsers = userService.getUnapprovedUsers();
        return ResponseEntity.ok(unapprovedUsers);
    }

    @GetMapping("/approvedUsers")
    public ResponseEntity<List<UserDto>> getApprovedUsers() {
        List<UserDto> unapprovedUsers = userService.getApprovedUsers();
        return ResponseEntity.ok(unapprovedUsers);
    }

    @PutMapping("/approveUser/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        userService.approveUserById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/assignInstructor/{id}")
    public ResponseEntity<?> asignInstructor(@PathVariable Long id){
        userService.assignInstructorByUserId(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/instructors")
    public ResponseEntity<?> getAllInstructors() {
        List<UserDto> instructors = userService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }
}
