package com.educational.portal.web;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.UserDto;
import com.educational.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addBankAccount")
    public ResponseEntity<?> addUserBankAccount(Principal principal, @Valid @RequestBody AddBankAccountRequest addBankAccountRequest){

        userService.addUserBankAccount(principal, addBankAccountRequest);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
