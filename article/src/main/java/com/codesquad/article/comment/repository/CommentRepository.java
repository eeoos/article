package com.codesquad.article.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.article.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	void deleteByArticleId(Long articleId);
	Slice<Comment> findByArticleId(Long articleId, Pageable pageable);
}
