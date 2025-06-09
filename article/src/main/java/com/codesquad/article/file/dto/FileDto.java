package com.codesquad.article.file.dto;

import java.util.List;

public class FileDto {

	private FileDto() {
	}

	public record UploadResponse(
		List<Long> uploadedFileIds
	) {
	}
}
