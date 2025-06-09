package com.codesquad.article.comment.service;

import org.springframework.stereotype.Service;

import com.codesquad.article.article.domain.Article;
import com.codesquad.article.article.repository.ArticleRepository;
import com.codesquad.article.comment.domain.Comment;
import com.codesquad.article.comment.dto.CommentDto;
import com.codesquad.article.comment.repository.CommentRepository;
import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	public CommentDto.CreateResponse createComment(Long articleId, CommentDto.CreateRequest request, Long firstUserId) {

		User user = userRepository.findById(firstUserId).orElseThrow(() ->new EntityNotFoundException("존재하지 않는 회원입니다."));
		Article article = articleRepository.findById(articleId).orElseThrow(() ->new EntityNotFoundException("존재하지 않는 게시글입니다."));

		Comment comment = Comment.builder()
			.content(request.content())
			.writer(user)
			.article(article)
			.build();

		Comment savedComment = commentRepository.save(comment);
		return new CommentDto.CreateResponse( savedComment.getArticle().getId(), savedComment.getId());
	}
}
