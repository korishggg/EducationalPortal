package com.educational.portal.web;

import com.educational.portal.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/users")
public class UserController {

//	TODO should be deleted this is test controller

	@GetMapping("/1")
	public ResponseEntity<?> getAllUsers1() {
		System.out.println("admin rights");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/2")
	public ResponseEntity<?> getAllUsers2() {
		System.out.println("manager rights");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/3")
	public ResponseEntity<?> getAllUsers3() {
		System.out.println("user rights");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/4")
	public ResponseEntity<?> getAllUser4() {
		System.out.println("user rights");
		return ResponseEntity.ok().build();
	}

}
