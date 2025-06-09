package com.codesquad.article.article.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesquad.article.article.domain.Article;
import com.codesquad.article.article.dto.ArticleDto;
import com.codesquad.article.article.repository.ArticleRepository;
import com.codesquad.article.comment.repository.CommentRepository;
import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public ArticleDto.CreateResponse createArticle(ArticleDto.CreateRequest request, Long loggedInUserId) {

		User writer = userRepository.findById(loggedInUserId).orElseThrow();
		Article article = Article.builder()
			.title(request.title())
			.content(request.content())
			.writer(writer)
			.build();

		Article savedArticle = articleRepository.save(article);
		return new ArticleDto.CreateResponse(savedArticle.getId());
	}

	@Transactional
	public void deleteArticle(Long articleId, Long loggedInUserId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
		Long writerId = article.getWriter().getId();

		if (writerId != loggedInUserId) {
			throw new RuntimeException("작성자만 삭제할 수 있습니다.");
		}
		commentRepository.deleteByArticleId(articleId);
		articleRepository.deleteById(articleId);
	}

	public void updateArticle(Long articleId, ArticleDto.UpdateRequest request, Long loggedInUserId) {
		Article article = articleRepository.findById(articleId).orElseThrow();

		if (article.getWriter().getId() != loggedInUserId) {
			throw new RuntimeException("수정할 권한이 없습니다.");
		}

		article.updateArticle(request.title(), request.content());
	}
}
