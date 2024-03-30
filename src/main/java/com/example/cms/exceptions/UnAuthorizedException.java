package com.example.cms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UnAuthorizedException extends RuntimeException {

	private String message;
}
