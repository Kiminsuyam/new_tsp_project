package com.tsp.new_tsp_project.exception;

import lombok.Getter;

@Getter
public enum ApiExceptionType implements BaseExceptionType {

	RUNTIME_EXCEPTION("SERVER_ERROR", 500, "서버에러"),
	BAD_REQUEST("", 401, "권한에러"),
	NOT_EXIST_IMAGE("UNAUTHORIZED", 500, "이미지 등록 에러"),
	ERROR_PRODUCTION("ERROR_PRODUCTION", 500, "프로덕션 등록 에러"),
	ERROR_PORTFOLIO("ERROR_PORTFOLIO", 500, "포트폴리오 등록 에러"),
	ERROR_MODEL("ERROR_MODEL", 500, "모델 등록 에러"),
	NOT_NULL("NOT_NULL", 400, "필수값 누락"),
	ID_EXIST("ERROR_REGIST",400,"같은 아이디 존재");

	private String errorCode;
	private int httpStatus;
	private String errorMessage;

	ApiExceptionType(String errorCode, int httpStatus, String errorMessage) {
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

}
