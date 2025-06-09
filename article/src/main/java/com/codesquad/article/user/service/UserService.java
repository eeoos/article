package com.codesquad.article.user.service;

import org.springframework.stereotype.Service;

import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.dto.UserDto;
import com.codesquad.article.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserDto.CreateResponse signUp(UserDto.CreateRequest request) {
		User user = User.builder()
			.userId(request.userId())
			.password(request.password())
			.name(request.name())
			.build();

		// 중복 이름 검증
		boolean exists = userRepository.existsByName(request.name());
		if (exists) {
			throw new RuntimeException("존재하는 사용자 이름입니다.");
		}
		User savedUser = userRepository.save(user);
		return new UserDto.CreateResponse(savedUser.getId());
	}
}
