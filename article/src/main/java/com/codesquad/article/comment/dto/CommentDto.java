package com.codesquad.article.comment.dto;

public class CommentDto {

	private CommentDto() {
	}

	public record CreateResponse(
		long issueId,
		long commentId
	) {
	}

	public record CreateRequest(
		String content
	) {
	}
}
