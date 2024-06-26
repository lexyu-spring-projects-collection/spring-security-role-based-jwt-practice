package com.lex.practice.service;

import com.lex.practice.entity.User;
import com.lex.practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : Lex Yu
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username is not found"));

		return user;

		/* First Part Practice
		if (!username.equals("Tester")) {
			throw new UsernameNotFoundException("Not Tester");
		}

		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "USER"));

		return new User(1L, "Tester", encoder.encode("p@ssw0rd"), roles);
		 */
	}
}
