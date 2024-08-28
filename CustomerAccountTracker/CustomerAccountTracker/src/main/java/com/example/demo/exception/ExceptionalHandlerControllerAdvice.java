package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionalHandlerControllerAdvice {

//	@ExceptionHandler(AccountAlreadyExistException.class)
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	@ResponseBody
//	ExceptionResponse handleResourceNotFoundException(AccountAlreadyExistException exception,
//			HttpServletRequest request) {
//		ExceptionResponse myResponse = new ExceptionResponse();
//		myResponse.setErrorMessage(exception.getMessage());
//		return myResponse;
//	}
//
//	@ExceptionHandler(InsufficientBalanceException.class)
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	ExceptionResponse handleInsufficientBalanceException(InsufficientBalanceException exception) {
//
//		ExceptionResponse response = new ExceptionResponse();
//		response.setErrorMessage(exception.getMessage());
//		return response;
//	}

//	@ExceptionHandler(TransactionFailException.class)
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	ExceptionResponse handletranscationFailexception(TransactionFailException exception) {
//
//		ExceptionResponse response = new ExceptionResponse();
//		response.setErrorMessage(exception.getMessage());
//		return response;
//	}
//
//	@ExceptionHandler(AmountCannotTransferException.class)
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	ExceptionResponse handleNegativeAmountCannotTransferException( AmountCannotTransferException exception) {
//
//		ExceptionResponse response = new ExceptionResponse();
//		response.setErrorMessage(exception.getMessage());
//		return response;
//	}
//
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ResponseBody
//	ExceptionResponse handleException(Exception exception, HttpServletRequest request) {
//		ExceptionResponse myResponse = new ExceptionResponse();
//		myResponse.setErrorMessage(exception.getMessage());
//		return myResponse;
//	}

}
