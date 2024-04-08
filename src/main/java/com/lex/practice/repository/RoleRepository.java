package com.lex.practice.repository;

import com.lex.practice.entity.Role;
import com.lex.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Lex Yu
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByAuthority(String authority);
}
