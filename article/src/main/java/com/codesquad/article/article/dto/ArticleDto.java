package com.codesquad.article.article.dto;

import jakarta.validation.constraints.NotBlank;

public class ArticleDto {

	private ArticleDto() {
	}

	public record CreateResponse(
		long id
	) {
	}

	public record CreateRequest(
		@NotBlank
		String title,

		@NotBlank
		String content
	) {
	}
}
