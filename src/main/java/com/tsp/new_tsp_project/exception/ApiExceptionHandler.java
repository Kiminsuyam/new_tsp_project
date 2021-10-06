package com.tsp.new_tsp_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.channels.AsynchronousCloseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

	@ExceptionHandler({TspException.class})
	public ResponseEntity<Error> exception(TspException tspException) {
		return new ResponseEntity<>(Error.create(tspException.getBaseExceptionType()), HttpStatus.OK);
	}

	@ExceptionHandler({
			MethodArgumentNotValidException.class,
			RuntimeException.class,
			BindException.class,
			HttpRequestMethodNotSupportedException.class,
			HttpMediaTypeException.class,
			HttpMediaTypeNotAcceptableException.class,
			MissingPathVariableException.class,
			TypeMismatchDataAccessException.class,
			HttpMessageNotReadableException.class,
			HttpMessageNotWritableException.class,
			NoHandlerFoundException.class,
			AsynchronousCloseException.class
	})
	public ResponseEntity<Object> validException(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult()
				.getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);
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
