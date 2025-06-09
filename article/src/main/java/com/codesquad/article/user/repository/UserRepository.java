package com.codesquad.article.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.article.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByName(String name);
}
