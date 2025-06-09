package com.codesquad.article.article.dto;

public class ArticleDto {

	private ArticleDto() {
	}

	public record CreateResponse(
		long id
	) {
	}

	public record CreateRequest(
		String title,
		String content
	) {
	}
}
