package com.tsp.new_tsp_project.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class RestResponse {

	private boolean success;
	private String message;
	private Map<String, Object> data;

	public RestResponse(Map<String, Object> data, boolean success, String message) {
		this.data = data;
		this.success = success;
		this.message = message;
	}

}
