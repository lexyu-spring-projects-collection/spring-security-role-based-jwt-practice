package com.lex.practice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Lex Yu
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@GetMapping
	public String helloUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("{}", auth.getAuthorities());
		log.info("{}", auth.getCredentials());
		log.info("{}", auth.getPrincipal());
		log.info("{}", auth.getDetails());

		return "User access Level";
	}

}
