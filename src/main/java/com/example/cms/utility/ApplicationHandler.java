package com.example.cms.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.cms.exceptions.BlogNotFoundByIdException;
import com.example.cms.exceptions.BlogPostAlreadyInDraftTypeException;
import com.example.cms.exceptions.BlogPostNotFoundById;
import com.example.cms.exceptions.ContributionPanelNotFoundByIdException;
import com.example.cms.exceptions.TitleAlreadyExistsException;
import com.example.cms.exceptions.TopicsNotSpecifiedException;
import com.example.cms.exceptions.UserAlreadyExistByEmailException;
import com.example.cms.exceptions.UserNotFoundByIdException;

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
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(UserNotFoundByIdException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"User By this ID doesn't Exist");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTitleAlreadyExists(TitleAlreadyExistsException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"Title Already exists change the title");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTopicsNotSpecified(TopicsNotSpecifiedException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"Topics Not specified it is null");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogNotFoundById(BlogNotFoundByIdException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"Blog Not Found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleContributionPanelNotFoundById(ContributionPanelNotFoundByIdException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"Panel Not Found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogPostNotFoundById(BlogPostNotFoundById ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"BlogPost Not Found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogPostAlreadyInDraftType(BlogPostAlreadyInDraftTypeException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), 
				"BlogPost in draft type");
	}
	
}
