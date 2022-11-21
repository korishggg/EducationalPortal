package com.educational.portal.web;

import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.service.UserService;
import com.educational.portal.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/approveUser/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        userService.approveUserById(id);
        return ResponseEntity.ok("User with this id = " + id + " is approved");
    }
    @PostMapping("/assignInstructor/{id}")
    public ResponseEntity<?> asignInstructor(@PathVariable Long id){
        userService.assignInstructorByUserId(id);
        return ResponseEntity.ok("User with this id = " + id + " been assigned with " + Constants.INSTRUCTOR_ROLE + " role");
    }
}
