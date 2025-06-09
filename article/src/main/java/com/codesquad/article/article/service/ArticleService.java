package com.codesquad.article.article.service;

import org.springframework.stereotype.Service;

import com.codesquad.article.article.domain.Article;
import com.codesquad.article.article.dto.ArticleDto;
import com.codesquad.article.article.repository.ArticleRepository;
import com.codesquad.article.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	public ArticleDto.CreateResponse createArticle(ArticleDto.CreateRequest request, Long firstUserId) {

		User writer = userRepository.findById(firstUserId);
		Article article = Article.builder()
			.title(request.title())
			.content(request.content())
			.writer(writer)
			.build();

		Article savedArticle = articleRepository.save(article);
		return new ArticleDto.CreateResponse(savedArticle.getId());
	}
}
