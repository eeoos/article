package com.codesquad.article.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.article.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByName(String name);

	Optional<User> findByUserId(String s);
}
