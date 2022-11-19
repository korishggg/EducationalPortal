package com.educational.portal.web;

import com.educational.portal.domain.entity.User;
import com.educational.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/unapprovedUsers")
    public ResponseEntity<List<?>> getUnapprovedUsers() {
        List<?> unapprovedUsers = userService.getUnapprovedUsers();
        return ResponseEntity.ok(unapprovedUsers);
    }

    @PostMapping("/approveUser/{id}")
    public ResponseEntity<User> approveUser(@PathVariable Long id) {
        User approvedUser = userService.approveUserById(id);
        return ResponseEntity.ok(approvedUser);
    }


}
