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
import com.codesquad.article.file.dto.FileDto;
import com.codesquad.article.file.service.FileService;
import com.codesquad.article.user.domain.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/v1/images",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ApiResponse<FileDto.UploadResponse>> uploadImages(
		@RequestPart("images")List<MultipartFile> images,
		@RequestPart("type") String type,
		@RequestPart("referenceId") Long referenceId,
		HttpSession session) {

		User loggedInUser = (User)session.getAttribute("loggedInUser");
		FileDto.UploadResponse response = fileService.uploadFile(images, type,referenceId, loggedInUser.getId());
		return ResponseEntity.ok(ApiResponse.success(
			response
		));
	}
}
