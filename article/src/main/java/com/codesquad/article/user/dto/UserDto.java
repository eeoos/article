package com.codesquad.article.user.dto;

import com.codesquad.article.user.domain.User;

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

	public record Response(
		long id,
		String name
	){
		public static Response from (User user) {
			return new Response(
				user.getId(),
				user.getName()
			);
		}

	}
}
