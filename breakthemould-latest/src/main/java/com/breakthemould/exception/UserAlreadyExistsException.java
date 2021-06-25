package com.breakthemould.exception;

public class UserAlreadyExistsException extends RuntimeException{
	
	public UserAlreadyExistsException() {}

	public UserAlreadyExistsException(String msg) {
		super(msg);
	}

}
