package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TitleAlreadyExistsException extends RuntimeException {

	private String message;
}
