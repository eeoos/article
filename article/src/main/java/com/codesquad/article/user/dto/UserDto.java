package com.codesquad.article.user.dto;

public class UserDto {

	private UserDto() {
	}

	public record CreateResponse(
		long id
	) {
	}

	public record CreateRequest(
		String userId,
		String password,
		String name
	) {
	}
}
