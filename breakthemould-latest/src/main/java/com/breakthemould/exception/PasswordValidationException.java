package com.breakthemould.exception;

public class PasswordValidationException extends RuntimeException {
	
	public PasswordValidationException() {}
	public PasswordValidationException(String msg) {
		super(msg);
	}

}
