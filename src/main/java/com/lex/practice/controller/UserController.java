package com.lex.practice.controller;

import com.lex.practice.service.AuthenticationService;
import com.lex.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : Lex Yu
 */
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@GetMapping("/")
	public String helloUser() {
		return "User access Level";
	}

}
