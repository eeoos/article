package com.codesquad.article.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

	private UserDto() {
	}

	public record CreateResponse(
		long id
	) {
	}

	public record CreateRequest(
		@NotBlank
		String userId,
		@NotBlank
		String password,
		@NotBlank
		String name
	) {
	}

	public record SignInResponse(
		long id
	) {
	}

	public record SignInRequest(
		String userId,
		String password
	) {
	}
}
