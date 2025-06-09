package com.codesquad.article.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.article.article.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
