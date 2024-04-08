package com.lex.practice.config;

import com.lex.practice.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

/**
 * @author : Lex Yu
 */
@Configuration
public class SecurityConfig {

	private final RSAKeyProperties keys;

	public SecurityConfig(RSAKeyProperties keys) {
		this.keys = keys;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console())
						.disable())
//				First Part Practice
//				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers(toH2Console()).permitAll();
					auth.requestMatchers("/auth/**").permitAll();
					auth.requestMatchers("/admin/**").hasRole("ADMIN");
					auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");
					auth.anyRequest().authenticated();
				})
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.oauth2ResourceServer(oauth2 -> {
					oauth2.jwt(Customizer.withDefaults());
					oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));
				})
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				Second Part Practice
//				.httpBasic(Customizer.withDefaults())
				.build();

	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService detailsService) {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();

		daoProvider.setUserDetailsService(detailsService);
		daoProvider.setPasswordEncoder(bCryptPasswordEncoder());

		return new ProviderManager(daoProvider);
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		Instant one_hour = Instant.now().plus(Duration.ofHours(1));

		JWK jwk = new RSAKey.Builder(keys.getPublicKey())
				.privateKey(keys.getPrivateKey())
				.expirationTime(Date.from(one_hour))
				.build();

		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
				= new JwtGrantedAuthoritiesConverter();

		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter =
				new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}

}
