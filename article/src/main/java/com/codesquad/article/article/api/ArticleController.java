package com.codesquad.article.article.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquad.article.article.dto.ArticleDto;
import com.codesquad.article.article.service.ArticleService;
import com.codesquad.article.common.dto.ApiResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ArticleController {

	private final ArticleService articleService;
	private static final Long FIRST_USER_ID = 1L;
	@PostMapping("/v1/artices")
	public ResponseEntity<ApiResponse<ArticleDto.CreateResponse>> createArticle(
		@Valid @RequestBody ArticleDto.CreateRequest request) throws URISyntaxException {
		ArticleDto.CreateResponse response = articleService.createArticle(request, FIRST_USER_ID);
		URI uri = new URI("/api/v1/artices" + response.id());
		return ResponseEntity.created(uri).body(ApiResponse.success(
			response
		));
	}
}
