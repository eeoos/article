package com.codesquad.article.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.codesquad.article.file.domain.File;
import com.codesquad.article.file.domain.FileUsageType;
import com.codesquad.article.file.dto.FileDto;
import com.codesquad.article.file.exception.ImageUploadException;
import com.codesquad.article.file.exception.ImageValidationException;
import com.codesquad.article.file.repository.FileRepository;
import com.codesquad.article.user.domain.User;
import com.codesquad.article.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {
	private static final long MAX_SIZE = 10485760L;
	private static final String ALLOW_extensions = "jpg,jpeg,png,gif,webp";
	public static final String IMAGES_URL = "/Users/jaehoonchoi/study/CodeSquad/be-article/article/article/images";
	private final FileRepository fileRepository;
	private final UserRepository userRepository;
	public FileDto.UploadResponse uploadFile(List<MultipartFile> images, String type, Long referenceId, Long loggedInUserId) {

		User user = userRepository.findById(loggedInUserId).orElseThrow();

		List<Long> savedIds = images.stream().map(image -> {
			validateFile(image);
			try {
				File file = createFile(image, type, referenceId, user);
				fileRepository.save(file);
				return file.getId();
			} catch (IOException e) {
				throw new ImageUploadException("이미지 업로드 중 오류 발생", e);
			}
		}).collect(Collectors.toList());

		return new FileDto.UploadResponse(savedIds);
	}

	private String getFileExtension(String filename) {
		if (!StringUtils.hasText(filename)) {
			return "";
		}
		int lastDotIndex = filename.lastIndexOf('.');
		return lastDotIndex == -1 ? "" : filename.substring(lastDotIndex + 1);
	}
	private void validateFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new ImageValidationException("업로드할 파일이 없습니다.");
		}

		// 파일 크기 검증
		if (file.getSize() > MAX_SIZE) {
			throw new ImageValidationException(
				String.format("파일 크기가 허용 범위 초과입니다. 최대 허용 범위=%s바이트", MAX_SIZE)
			);
		}

		// 확장자 검증
		String originalFilename = file.getOriginalFilename();
		if (!StringUtils.hasText(originalFilename)) {
			throw new ImageValidationException("파일명이 유효하지 않습니다.");
		}

		String extension = getFileExtension(originalFilename).toLowerCase();
		String[] allowedExtensions = ALLOW_extensions.split(",");

		boolean isAllowed = Arrays.stream(allowedExtensions)
			.anyMatch(ext -> ext.trim().toLowerCase().equals(extension));

		if (!isAllowed) {
			throw new ImageValidationException(
				String.format("지원하지 않는 파일 형식, 허용된 형식: %s", String.join(", ", allowedExtensions))
			);
		}

		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new ImageValidationException("이미지 파일만 업로드 가능합니다.");
		}
	}

	private File createFile(MultipartFile file, String typeStr, Long referenceId, User user) throws IOException {

		FileUsageType type = FileUsageType.fromTypeStr(typeStr);
		String originalName = file.getOriginalFilename();
		String extension = getFileExtension(originalName);

		String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String uuid = UUID.randomUUID().toString();
		String storedName = uuid + "." + extension;

		Path directory = Paths.get(IMAGES_URL, datePath);
		Files.createDirectories(directory);

		Path storedPath = directory.resolve(storedName);
		file.transferTo(storedPath.toFile());

		return File.builder()
			.path(storedPath.toString())
			.originalName(originalName)
			.storedName(storedName)
			.size(file.getSize())
			.type(type)
			.referenceId(referenceId)
			.uploadBy(user)
			.build();
	}
}
