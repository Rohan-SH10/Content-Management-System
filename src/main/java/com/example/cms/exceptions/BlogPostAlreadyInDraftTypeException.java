package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogPostAlreadyInDraftTypeException extends RuntimeException {

	private String message;
}
