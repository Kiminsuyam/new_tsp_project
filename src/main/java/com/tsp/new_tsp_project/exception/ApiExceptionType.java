package com.tsp.new_tsp_project.exception;

import lombok.Getter;

@Getter
public enum ApiExceptionType implements BaseExceptionType {

	RUNTIME_EXCEPTION(1001, 500, "서버에러"),
	BAD_REQUEST(1002, 401, "권한에러"),
	NOT_EXIST_IMAGE(1003, 500, "이미지 등록 에러"),
	ERROR_PRODUCTION(1004, 500, "프로덕션 등록 에러"),
	ERROR_PORTFOLIO(1005, 500, "포트폴리오 등록 에러");

	private int errorCode;
	private int httpStatus;
	private String errorMessage;

	ApiExceptionType(int errorCode, int httpStatus, String errorMessage) {
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

}
