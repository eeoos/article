package com.codesquad.article.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codesquad.article.common.dto.ApiResponse;
import com.codesquad.article.file.exception.ImageUploadException;
import com.codesquad.article.file.exception.ImageValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ImageValidationException.class)
	public ResponseEntity<ApiResponse<?>> handleImageValidationException(ImageValidationException e) {
		log.warn("ImageValidationException: {}", e.getMessage());
		return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
	}

	@ExceptionHandler(ImageUploadException.class)
	public ResponseEntity<ApiResponse<?>> handleImageUploadException(ImageUploadException e) {
		log.error("ImageUploadException: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("이미지 업로드 중 오류가 발생했습니다."));
	}
}
