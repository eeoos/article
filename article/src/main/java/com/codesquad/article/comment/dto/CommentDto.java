package com.codesquad.article.comment.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentDto {

	private CommentDto() {
	}

	public record CreateResponse(
		long issueId,
		long commentId
	) {
	}

	public record CreateRequest(
		@NotBlank
		String content
	) {
	}
}
