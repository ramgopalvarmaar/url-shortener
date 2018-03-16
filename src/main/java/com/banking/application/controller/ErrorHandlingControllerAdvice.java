package com.banking.application.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.banking.application.exception.InSufficientBalanceException;


@ControllerAdvice(assignableTypes = { BankingApplicationController.class })
public class ErrorHandlingControllerAdvice {
	
	@ExceptionHandler(value = InSufficientBalanceException.class)
	public ResponseEntity<String> handleInvalidClientIdException(InSufficientBalanceException exception) throws IOException {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}