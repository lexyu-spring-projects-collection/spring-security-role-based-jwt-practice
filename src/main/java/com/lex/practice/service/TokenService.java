package com.lex.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Lex Yu
 */
@Service
public class TokenService {
	@Autowired
	private JwtEncoder jwtEncoder;
	@Autowired
	private JwtDecoder jwtDecoder;

	public String generateJwt(Authentication auth) {
		Instant now = Instant.now();
		Instant one_hour = Instant.now().plus(Duration.ofHours(1));

		String scope = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("localhost")
				.issuedAt(now)
				.subject(auth.getName())
				.claim("roles", scope)
				.id(UUID.randomUUID().toString())
				.expiresAt(one_hour)
				.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

}
