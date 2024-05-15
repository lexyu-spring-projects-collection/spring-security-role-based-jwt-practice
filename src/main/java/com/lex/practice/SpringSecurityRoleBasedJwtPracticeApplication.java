package com.lex.practice;

import com.lex.practice.entity.Role;
import com.lex.practice.entity.User;
import com.lex.practice.repository.RoleRepository;
import com.lex.practice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static com.lex.practice.utils.KeyGeneratorUtility.generateRsaKey;

@SpringBootApplication
public class SpringSecurityRoleBasedJwtPracticeApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityRoleBasedJwtPracticeApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder){
//		return args -> {
//			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
//
//			Role adminRole = roleRepository.save(new Role("ADMIN"));
//			Role userRole = roleRepository.save(new Role("USER"));
//
//			Set<Role> roles = new HashSet<>();
//			roles.add(adminRole);
//
//			User admin = new User(1L, "admin", encoder.encode("P@SSW0RD"), roles);
//
//			userRepository.save(admin);
//			generateRsaKey();
//		};
//	}
}
