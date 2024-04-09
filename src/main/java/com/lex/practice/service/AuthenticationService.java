package com.lex.practice.service;

import com.lex.practice.entity.Role;
import com.lex.practice.entity.User;
import com.lex.practice.model.LoginResponseDTO;
import com.lex.practice.model.UserDTO;
import com.lex.practice.repository.RoleRepository;
import com.lex.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Lex Yu
 */
@Service
@Transactional
public class AuthenticationService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	@Autowired
	public AuthenticationService(PasswordEncoder encoder, UserRepository userRepository, RoleRepository roleRepository,
	                             AuthenticationManager authenticationManager, TokenService tokenService) {
		this.encoder = encoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	public User registerUser(String username, String password) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		String encodedPassword = encoder.encode(password);

		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();

		authorities.add(userRole);

		return userRepository.save(new User(0L, username, encodedPassword, authorities));
	}


	public LoginResponseDTO loginUser(String username, String password) {

		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password)
			);

			String token = tokenService.generateJwt(auth);

			User user = userRepository.findByUsername(username).get();
			UserDTO userDTO = new UserDTO(user.getUserId(), user.getUsername(), user.getAuthorities());

			return new LoginResponseDTO(userDTO, token);
		} catch (AuthenticationException ex) {
			return new LoginResponseDTO(null, "auth err");
		}
	}
}
