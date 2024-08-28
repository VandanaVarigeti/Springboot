package com.example.demo.exception;

public class AmountCannotTransferException extends Exception {
	private static final long serialVersionUID = 1L;

	public AmountCannotTransferException() {
		super();
	}

	public AmountCannotTransferException(String message) {
		super(message);
	}



}
