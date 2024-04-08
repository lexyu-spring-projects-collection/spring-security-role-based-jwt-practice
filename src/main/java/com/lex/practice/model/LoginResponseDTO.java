package com.lex.practice.model;

import com.lex.practice.entity.User;

/**
 * @author : Lex Yu
 */
public record LoginResponseDTO(UserDTO userDTO, String jwt) {
}
