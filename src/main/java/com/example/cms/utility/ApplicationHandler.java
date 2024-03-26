package com.example.cms.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exceptions.UserAlreadyExistByEmailException;

@RestControllerAdvice
public class ApplicationHandler {
	@Autowired
	private ErrorStructure<String> structure;
	private ResponseEntity<ErrorStructure<String>>  errorResponse(HttpStatus status,String message, String errorData){
		return new ResponseEntity<ErrorStructure<String>>(structure.setErrorCode(status.value())
				.setErrorData(errorData)
				.setErrorMessage(message),status);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"User already registered");
	}
	
}
