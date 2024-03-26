package com.example.cms.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ErrorStructure<T> {

	private int errorCode;
	private String errorMessage;
	private T errorData;
	public ErrorStructure<T> setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	public ErrorStructure<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	public ErrorStructure<T> setErrorData(T errorData) {
		this.errorData = errorData;
		return this;
	}
}
