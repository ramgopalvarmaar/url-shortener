package com.url.shortener.exception;

public class InvalidUrlException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidUrlException(String message) {
		super(message);
	}
}
