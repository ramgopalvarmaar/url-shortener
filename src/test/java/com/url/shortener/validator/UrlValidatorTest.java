package com.url.shortener.validator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.url.shortener.controller.BaseTest;
import com.url.shortener.entity.User;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.InvalidClientIdException;
import com.url.shortener.exception.InvalidUrlException;

public class UrlValidatorTest extends BaseTest{
	
	@InjectMocks
    private UrlValidator urlValidator;
	
	@Mock
	UserRepository userRepository;

    @Test
    public void validateUrl_returnsIfApiKeyIsPresentInDb() throws Exception {
    	String clientId = RandomStringUtils.randomAlphabetic(5);
    	String url = "http://example.com";
    	when(userRepository.findByApiKey(clientId)).thenReturn(new User());
    	urlValidator.validateUrl(clientId, url);
    	verify(userRepository).findByApiKey(clientId);
    }
    
    @Test(expected=InvalidClientIdException.class)
    public void validateUrl_throwsInvalidClientIdException_whenNotPresentInDb() throws Exception {
    	String clientId = RandomStringUtils.randomAlphabetic(5);
    	String url = "http://example.com";
    	when(userRepository.findByApiKey(clientId)).thenReturn(null);
    	urlValidator.validateUrl(clientId, url);
    	verify(userRepository).findByApiKey(clientId);
    }
    
    @Test(expected=InvalidUrlException.class)
    public void validateUrl_throwsInvalidUrlException_whenInvalidUrl() throws Exception {
    	String clientId = RandomStringUtils.randomAlphabetic(5);
    	String url = RandomStringUtils.randomAlphabetic(5);
    	when(userRepository.findByApiKey(clientId)).thenReturn(new User());
    	urlValidator.validateUrl(clientId, url);
    	verify(userRepository).findByApiKey(clientId);
    }
    
}
