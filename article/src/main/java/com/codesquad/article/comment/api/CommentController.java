package com.codesquad.article.comment.api;

import static org.springframework.data.domain.Sort.Direction.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.article.comment.dto.CommentDto;
import com.codesquad.article.comment.service.CommentService;
import com.codesquad.article.common.dto.ApiResponse;
import com.codesquad.article.user.domain.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/v1/articles/{articleId}/comments")
	public ResponseEntity<ApiResponse<CommentDto.CreateResponse>> createComment(
		@PathVariable Long articleId,
		@Valid @RequestBody CommentDto.CreateRequest request,
		HttpSession session) throws URISyntaxException {
		User loggedInUser = (User)session.getAttribute("loggedInUser");

		CommentDto.CreateResponse response= commentService.createComment(articleId, request, loggedInUser.getId());
		URI uri = new URI("/api/v1/issues/" + response.issueId() + "comments/" + response.commentId());
		return ResponseEntity.created(uri).body(ApiResponse.success(
				response
			)
		);
	}

	@GetMapping("/v1/articles/{articleId}/comments")
	public ResponseEntity<ApiResponse<CommentDto.ListResponse>> findComments(
		@PathVariable Long articleId,
		@PageableDefault(size = 20, sort = "createdAt", direction = DESC) Pageable pageable
	){
		CommentDto.ListResponse response = commentService.findComments(articleId, pageable);
		return ResponseEntity.ok(
			ApiResponse.success(
				response
			)
		);
	}
}
