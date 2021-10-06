package com.tsp.new_tsp_project.exception;

public interface BaseExceptionType {

	String getErrorCode();
	int getHttpStatus();
	String getErrorMessage();
}
