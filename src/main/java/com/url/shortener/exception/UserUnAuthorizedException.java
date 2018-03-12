package com.url.shortener.exception;

public class UserUnAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserUnAuthorizedException(String message) {
		super(message);
	}
}
