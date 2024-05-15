package com.lex.practice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Lex Yu
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

	@GetMapping
	public String helloAdmin() {
		return "Admin access Level";
	}
}
