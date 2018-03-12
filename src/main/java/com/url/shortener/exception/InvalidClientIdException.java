package com.url.shortener.exception;

public class InvalidClientIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidClientIdException(String message) {
		super(message);
	}
}
