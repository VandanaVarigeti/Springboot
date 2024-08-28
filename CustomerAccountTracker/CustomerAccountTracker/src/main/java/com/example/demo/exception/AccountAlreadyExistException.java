package com.example.demo.exception;

public class AccountAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistException() {
		super();
	}

	public AccountAlreadyExistException(String message) {
		super(message);
	}

}
