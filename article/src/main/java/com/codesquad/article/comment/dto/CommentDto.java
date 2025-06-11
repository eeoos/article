package com.codesquad.article.comment.dto;

import org.springframework.data.domain.Slice;

import com.codesquad.article.comment.domain.Comment;
import com.codesquad.article.user.dto.UserDto;

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

	public record ListResponse(
		Slice<ListItemResponse> comments
	) {
	}

	public record ListItemResponse(
		long commentId,
		long articleId,
		String content,
		UserDto.Response writer
	){
		public static ListItemResponse from(Comment comment) {
			return new ListItemResponse(
				comment.getId(),
				comment.getArticle().getId(),
				comment.getContent(),
				UserDto.Response.from(comment.getWriter())
			);
		}
	}
}
