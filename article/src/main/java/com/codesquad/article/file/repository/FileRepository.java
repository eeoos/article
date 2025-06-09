package com.codesquad.article.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesquad.article.file.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
