package com.example.demo.exception;

public class TransactionFailException extends Exception{

	private static final long serialVersionUID = 1L;

	public TransactionFailException() {
		super();
	}

	public TransactionFailException(String message) {
		super(message);
	}


}
