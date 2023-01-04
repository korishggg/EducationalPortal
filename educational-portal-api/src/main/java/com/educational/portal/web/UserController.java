package com.educational.portal.web;

import com.educational.portal.domain.dto.AddBankAccountRequest;
import com.educational.portal.domain.dto.UserInfoDto;
import com.educational.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoDto> userInfo(Principal principal) {
        var userInfo = userService.getUserInfoForAuthorizedUser(principal);
        return ResponseEntity.ok()
                             .body(userInfo);
    }
}
