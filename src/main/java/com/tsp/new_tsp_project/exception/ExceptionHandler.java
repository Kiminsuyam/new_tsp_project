package com.tsp.new_tsp_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(TspException.class)
	public ResponseEntity<Error> exception(TspException tspException) {
		return new ResponseEntity<>(Error.create(tspException.getBaseExceptionType()), HttpStatus.OK);
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse> validException(MethodArgumentNotValidException ex) {
		RestResponse restResponse = new RestResponse(false, "유효성 검사 실패 : " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

		return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	static class Error {
		private int code;
		private int status;
		private String message;

		static Error create(BaseExceptionType exception) {
			return new Error(exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage());
		}
	}
}
