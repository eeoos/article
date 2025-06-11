package com.codesquad.article.article.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.codesquad.article.article.domain.Article;
import com.codesquad.article.user.dto.UserDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

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

	public record UpdateRequest(
		@NotBlank
		String title,

		@NotBlank
		String content
	) {
	}

	public record ListResponse(
		Page<ListItemResponse> articles
	) {
	}

	@Builder
	public record ListItemResponse(
		Long id,
		String title,
		String content,
		UserDto.Response writer
	){
		public static ListItemResponse from(Article article) {
			return ListItemResponse.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.writer(UserDto.Response.from(article.getWriter()))
				.build();
		}
	}
}
