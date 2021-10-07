package com.tsp.new_tsp_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({TspException.class})
	public ResponseEntity<Error> exception(TspException tspException) {
		return new ResponseEntity<>(Error.create(tspException.getBaseExceptionType()), HttpStatus.OK);
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<RestResponse> validException(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
//		Map<String, Object> body = new HashMap<>();
//		body.put("timestamp", new Date());
//		body.put("status", status.value());
//
//		List<String> errors = ex.getBindingResult()
//				.getFieldErrors().stream().map(x -> x.getDefaultMessage())
//				.collect(Collectors.toList());
//
//		body.put("errors", errors);
//
//		RestResponse restResponse = new RestResponse(body, false, ex.getAllErrors().get(0).getDefaultMessage());
//
//		return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponse error = new ErrorResponse("Validation Failed", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	static class Error {
		private String code;
		private int status;
		private String message;

		static Error create(BaseExceptionType exception) {
			return new Error(exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage());
		}
	}
}
