package com.codesquad.article.user.service;

import org.springframework.stereotype.Service;

import com.codesquad.article.user.dto.UserDto;
import com.codesquad.article.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserDto.CreateResponse signUp(UserDto.CreateRequest request) {
	}
}
