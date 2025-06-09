package com.codesquad.article.file.exception;

public class FileUsageTypeException extends RuntimeException {
	public FileUsageTypeException(String message) {
		super(message);
	}

	public FileUsageTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
