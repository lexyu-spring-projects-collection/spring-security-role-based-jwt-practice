package com.lex.practice.model;

import com.lex.practice.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @author : Lex Yu
 */
public record UserDTO(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
}
