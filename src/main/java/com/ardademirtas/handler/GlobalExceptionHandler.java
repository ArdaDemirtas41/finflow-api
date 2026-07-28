package com.ardademirtas.handler;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ardademirtas.exception.BaseException;
import com.ardademirtas.exception.ErrorMessage;
import com.ardademirtas.exception.MessageType;

import tools.jackson.databind.exc.InvalidFormatException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = BaseException.class)
	public ResponseEntity<ApiError<String>> handleBaseException(BaseException baseException, WebRequest request) {

		return ResponseEntity.badRequest().body(createApiError(baseException.getMessage(), request));

	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception, WebRequest request) {

		Map<String, List<String>> errorsMap = new HashMap<>();

		for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {

			String fieldName = ((FieldError) objectError).getField();

			if (errorsMap.containsKey(fieldName)) {

				errorsMap.put(fieldName, getMapList(errorsMap.get(fieldName), objectError.getDefaultMessage()));

			} else {

				errorsMap.put(fieldName, getMapList(new ArrayList<>(), objectError.getDefaultMessage()));
			}

		}

		return ResponseEntity.badRequest().body(createApiError(errorsMap, request));

	}

	public <T> ApiError<T> createApiError(T error, WebRequest request) {

		ApiError<T> apiError = new ApiError<>();

		apiError.setStatus(HttpStatus.BAD_REQUEST.value());

		Exception<T> exception = new Exception<>();
		exception.setHostName(getHostName());
		exception.setPath(request.getDescription(false));
		exception.setException_date(new Date());

		exception.setException(error);

		apiError.setException(exception);

		return apiError;

	}

	private String getHostName() {

		try {
			String hostName = InetAddress.getLocalHost().getHostName();
			return hostName;
		} catch (UnknownHostException e) {

			throw new BaseException(new ErrorMessage(MessageType.INETADDRES_NOT_FOUND, e.getMessage()));
		}
	}

	private List<String> getMapList(List<String> list, String value) {

		list.add(value);
		return list;
	}
	

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError<String>> handleHttpMessageNotReadableException(
	        HttpMessageNotReadableException ex, WebRequest request) {

	    Throwable cause = ex.getMostSpecificCause();

	    if (cause instanceof InvalidFormatException invalidFormatException) {

	        Class<?> targetType = invalidFormatException.getTargetType();

	        String fieldName = invalidFormatException.getPath().isEmpty()
	                ? "unknown"
	                : invalidFormatException.getPath().get(0).getPropertyName();

	        if (targetType.isEnum()) {

	            Object[] constants = targetType.getEnumConstants();

	            Stream<Object> stream = Arrays.stream(constants);

	            Stream<String> stringStream = stream.map(Object::toString);

	            String allowedValues = stringStream.collect(Collectors.joining(", "));

	            String message = "Invalid value for field "+fieldName+ " Allowed values are : "+allowedValues;

	            return ResponseEntity.badRequest().body(createApiError(message, request));
	        }
	        if (targetType.equals(Long.class) || targetType.equals(Integer.class)) {
	            return ResponseEntity.badRequest().body(createApiError(
	                    "Field '" + fieldName + "' must be a valid whole number.", request));
	        }

	        if (targetType.equals(BigDecimal.class) || targetType.equals(Double.class)) {
	            return ResponseEntity.badRequest().body(createApiError(
	                    "Field '" + fieldName + "' must be a valid decimal number.", request));
	        }

	        if (targetType.equals(Date.class) || targetType.equals(Date.class)) {
	            return ResponseEntity.badRequest().body(createApiError(
	                    "Field '" + fieldName + "' must be a valid date in format yyyy-MM-dd.", request));
	        }

	        return ResponseEntity.badRequest().body(createApiError(
	                "Field '" + fieldName + "' has an invalid value.", request));
	    }

	    return ResponseEntity.badRequest().body(createApiError("Malformed JSON request.", request));
	}
	
}
