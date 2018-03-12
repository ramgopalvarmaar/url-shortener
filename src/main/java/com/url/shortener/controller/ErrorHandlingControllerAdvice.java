package com.url.shortener.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.url.shortener.controller.model.ShortenUrlResponse;
import com.url.shortener.exception.InvalidClientIdException;
import com.url.shortener.exception.InvalidUrlException;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.exception.UserUnAuthorizedException;
import com.url.shortener.login.controller.LoginController;

@ControllerAdvice(assignableTypes = { UrlController.class, LoginController.class })
public class ErrorHandlingControllerAdvice {

	@ExceptionHandler(value = UrlNotFoundException.class)
	public void handleUrlNotFoundException(HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
	@ExceptionHandler(value = InvalidClientIdException.class)
	public ResponseEntity<ShortenUrlResponse> handleInvalidClientIdException(InvalidClientIdException exception) throws IOException {
		ShortenUrlResponse response =  new ShortenUrlResponse(null,exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidUrlException.class)
	public ResponseEntity<ShortenUrlResponse> handleInvalidUrlException(InvalidUrlException exception) throws IOException {
		ShortenUrlResponse response =  new ShortenUrlResponse(null,exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UserUnAuthorizedException.class)
	public ModelAndView handleUserUnAuthorizedException(UserUnAuthorizedException exception) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("signup");
		modelAndView.addObject("status", exception.getMessage());
		return modelAndView;
	}
}