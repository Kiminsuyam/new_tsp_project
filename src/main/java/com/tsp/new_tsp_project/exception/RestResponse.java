package com.tsp.new_tsp_project.exception;

import lombok.Getter;

@Getter
public class RestResponse {

	private boolean success;
	private String message;
	private Object data;

	public RestResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

}
