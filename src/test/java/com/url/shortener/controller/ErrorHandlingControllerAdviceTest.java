package com.url.shortener.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.url.shortener.controller.model.ShortenUrlResponse;
import com.url.shortener.exception.InvalidClientIdException;
import com.url.shortener.exception.InvalidUrlException;
import com.url.shortener.exception.UserUnAuthorizedException;

public class ErrorHandlingControllerAdviceTest extends BaseTest {

    @InjectMocks
    private ErrorHandlingControllerAdvice errorHandlingControllerAdvice;

    @Test
    public void handleInvalidClientIdException_returnsShortenUrlResponseWithError() throws IOException {
    	InvalidClientIdException invalidClientIdException = new InvalidClientIdException("Invalid ClientId");
    	ResponseEntity<ShortenUrlResponse> response = errorHandlingControllerAdvice.handleInvalidClientIdException(invalidClientIdException);
        assertThat(response.getBody().getShortenedUrl(), equalTo(null));
        assertThat(response.getBody().getMessage(), equalTo("Invalid ClientId"));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void handleInvalidUrlException_returnsShortenUrlResponseWithError() throws IOException {
    	InvalidUrlException invalidUrlException = new InvalidUrlException("Invalid Url");
    	ResponseEntity<ShortenUrlResponse> response = errorHandlingControllerAdvice.handleInvalidUrlException(invalidUrlException);
        assertThat(response.getBody().getShortenedUrl(), equalTo(null));
        assertThat(response.getBody().getMessage(), equalTo("Invalid Url"));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
    
    @Test
    public void handleUserUnAuthorizedException_returnsErrorMessageInModalToUI() throws IOException {
    	UserUnAuthorizedException unAuthorizedException = new UserUnAuthorizedException("Invalid user");
    	ModelAndView result = errorHandlingControllerAdvice.handleUserUnAuthorizedException(unAuthorizedException);
        assertThat(result.getViewName(), equalTo("signup"));
        assertThat(result.getModel().get("status"), equalTo("Invalid user"));
    }


}