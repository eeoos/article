package com.codesquad.article.file.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codesquad.article.common.dto.ApiResponse;
import com.codesquad.article.file.domain.FileUsageType;
import com.codesquad.article.file.dto.FileDto;
import com.codesquad.article.file.service.FileService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/v1/images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<FileDto.UploadResponse>> uploadImages(
		@RequestPart("images")List<MultipartFile> images,
		@RequestPart("type") FileUsageType type,
		@RequestPart("referenceId") Long referenceId) {
		FileDto.UploadResponse response = fileService.uploadFile(images, type,referenceId );
		return ResponseEntity.ok(ApiResponse.success(
			response
		));
	}
}
