package com.codesquad.article.article.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.article.article.dto.ArticleDto;
import com.codesquad.article.article.service.ArticleService;
import com.codesquad.article.common.dto.ApiResponse;
import com.codesquad.article.user.domain.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ArticleController {

	private final ArticleService articleService;

	@PostMapping("/v1/articles")
	public ResponseEntity<ApiResponse<ArticleDto.CreateResponse>> createArticle(
		@Valid @RequestBody ArticleDto.CreateRequest request,
		HttpSession session) throws URISyntaxException {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		ArticleDto.CreateResponse response = articleService.createArticle(request, loggedInUser.getId());
		URI uri = new URI("/api/v1/articles" + response.id());
		return ResponseEntity.created(uri).body(ApiResponse.success(
			response
		));
	}

	@DeleteMapping("/v1/articles/{articleId}")
	public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
		HttpSession session) {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		articleService.deleteArticle(articleId, loggedInUser.getId());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/v1/articles/{articleId}")
	public ResponseEntity<Void> updateArticle(
		@PathVariable Long articleId,
		@Valid @RequestBody ArticleDto.UpdateRequest request,
		HttpSession session) {
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		articleService.updateArticle(articleId, request, loggedInUser.getId());
		return ResponseEntity.noContent().build();
	}
}
