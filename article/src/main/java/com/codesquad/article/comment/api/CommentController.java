package com.codesquad.article.comment.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.article.comment.dto.CommentDto;
import com.codesquad.article.comment.service.CommentService;
import com.codesquad.article.common.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;
	private static final Long FIRST_USER_ID = 1L;

	@PostMapping("/v1/articles/{articleId}/comments")
	public ResponseEntity<ApiResponse<CommentDto.CreateResponse>> createComment(
		@PathVariable Long articleId,
		@Valid @RequestBody CommentDto.CreateRequest request) throws URISyntaxException {

		CommentDto.CreateResponse response= commentService.createComment(articleId, request, FIRST_USER_ID);
		URI uri = new URI("/api/v1/issues/" + response.issueId() + "comments/" + response.commentId());
		return ResponseEntity.created(uri).body(ApiResponse.success(
				response
			)
		);
	}
}
