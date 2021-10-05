package com.tsp.new_tsp_project.exception;

public interface BaseExceptionType {

	int getErrorCode();
	int getHttpStatus();
	String getErrorMessage();
}
