package com.lex.practice.controller;

import com.lex.practice.entity.User;
import com.lex.practice.model.LoginResponseDTO;
import com.lex.practice.model.RegistrationDTO;
import com.lex.practice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : Lex Yu
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registration) {

		authenticationService.registerUser(registration.username(), registration.password());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegistrationDTO body) {

		LoginResponseDTO resp = authenticationService.loginUser(body.username(), body.password());

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
}
