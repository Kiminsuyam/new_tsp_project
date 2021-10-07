package com.tsp.new_tsp_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ErrorResponse {

	public ErrorResponse(HttpStatus status, Map<String, Object> error) {
		super();
		this.status = status;
		this.error = error;
	}

	//General error message about nature of error
	private HttpStatus status;

	//Specific errors in API request processing
	private Map<String, Object> error;

	//Getter and setters
}
