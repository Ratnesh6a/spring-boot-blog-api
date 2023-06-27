package com.blog.app.exception;


import java.util.HashMap;
import java.util.Map;

import com.blog.app.exceptionresponses.ResponseErrors;
import com.blog.app.util.Constants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.app.response.ApiResponse;
 
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.core.NestedExceptionUtils.getMostSpecificCause;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
	
	@ExceptionHandler(ResouceNotFoundException.class)
	public ResponseEntity<ApiResponse>resourceNotFoundException(ResouceNotFoundException toGetMsg){
		String message = toGetMsg.getMessage();
		ApiResponse  apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.SEE_OTHER);
		
	} 
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException toGetMsg) {
	    Map<String, String> errors = new HashMap<>();
	    toGetMsg.getConstraintViolations().forEach(violation -> {
	        String fieldName = violation.getPropertyPath().toString();
	        String message = violation.getMessage();
	        errors.put(fieldName, message);
	    });
	    return ResponseEntity.badRequest().body(errors);
	}   
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleWrongRequestException(HttpRequestMethodNotSupportedException toGetMsg) {
	    Map<String, String> errors = new HashMap<>();
	    String message = toGetMsg.getMethod();
	    errors.put( message+" method is not Allowed ",   "The Actual method is "+toGetMsg.getSupportedHttpMethods().toString());
	    
	    return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> alreadyExistException(DataIntegrityViolationException duplicateData) {
//		Map<String, String> errors = new HashMap<>();
		String message = duplicateData.getMostSpecificCause().getMessage();
//		errors.put( message+" method is not Allowed ",   "The Actual method is "+duplicateData);
		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseErrors> parameterWrongException(MethodArgumentTypeMismatchException givenParameter) {
		String parameterType = givenParameter.getParameter().getParameterType().getSimpleName();;
		String message = "Given Parameters must be of type " + parameterType;
		ResponseErrors responseErrors= new ResponseErrors(Constants.BAD_REQUEST,HttpStatus.BAD_REQUEST,message);

		return new ResponseEntity(responseErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseErrors> blankContentException(IllegalArgumentException givenPage) {
		String message = "The content cannot be null";
		ResponseErrors responseErrors= new ResponseErrors(Constants.BAD_REQUEST,HttpStatus.BAD_REQUEST,message);
		return new ResponseEntity(responseErrors, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<ResponseErrors> invalidImage(ImageNotFoundException img)
	{
		String message = "Image type should be JPG";
		ResponseErrors responseErrors = new ResponseErrors(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST, message);
		return new ResponseEntity<>(responseErrors,HttpStatus.BAD_REQUEST);
	}

}
 