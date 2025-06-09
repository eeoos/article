package com.codesquad.article.comment.service;

import com.codesquad.article.article.domain.Article;
import com.codesquad.article.article.repository.ArticleRepository;
import com.codesquad.article.comment.domain.Comment;
import com.codesquad.article.comment.dto.CommentDto;
import com.codesquad.article.comment.repository.CommentRepository;
import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;

	public CommentDto.CreateResponse createComment(Long issueId, CommentDto.CreateRequest request, Long firstUserId) {

		User user = userRepository.findById(firstUserId).orElseThrow();
		Article article = articleRepository.findById(issueId).orElseThrow();

		Comment comment = Comment.builder()
			.content(request.content())
			.writer(user)
			.article(article)
			.build();

		Comment savedComment = commentRepository.save(comment);
		return new CommentDto.CreateResponse(savedComment.getId(), savedComment.getArticle().getId());
	}
}
