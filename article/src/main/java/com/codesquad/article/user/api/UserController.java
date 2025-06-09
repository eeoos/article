package com.codesquad.article.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.article.common.dto.ApiResponse;
import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.dto.UserDto;
import com.codesquad.article.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/v1/signup")
	public ResponseEntity<ApiResponse<UserDto.CreateResponse>> signUp(
		@Valid @RequestBody UserDto.CreateRequest request) {

		UserDto.CreateResponse response = userService.signUp(request);
		return ResponseEntity.ok(ApiResponse.success(
			response
		));
	}

	@PostMapping("/v1/signin")
	public ResponseEntity<ApiResponse<UserDto.SignInResponse>> signIn(
		@Valid @RequestBody UserDto.SignInRequest request,
		HttpSession session
	) {
		User user = userService.signIn(request);
		session.setAttribute("loggedInUser", user);
		return ResponseEntity.ok(ApiResponse.success(
			new UserDto.SignInResponse(user.getId())
		));
	}
}
