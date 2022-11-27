package com.educational.portal.web;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addBankAccount")
    public ResponseEntity<?> addUserBankAccount(Principal principal, @RequestBody AddBankAccountRequest addBankAccountRequest){

        userService.addUserBankAccount(principal, addBankAccountRequest);

        return ResponseEntity.ok()
                .build();
    }
}
