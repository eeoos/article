package com.codesquad.article.file.domain;

import com.codesquad.article.file.exception.FileUsageTypeException;

public enum FileUsageType {
	ARTICLE, COMMENT;

	public static FileUsageType fromTypeStr(String typeStr) {

		if (typeStr == null) {
			throw new FileUsageTypeException("파일 타입은 null일 수 없습니다.");
		}

		for (FileUsageType type : values()) {
			if (type.name().equalsIgnoreCase(typeStr)) {
				return type;
			}
		}

		throw new FileUsageTypeException("정해진 파일 타입이 아닙니다. ARTICLE, COMMENT 중 선택해주세요.");
	}
}
