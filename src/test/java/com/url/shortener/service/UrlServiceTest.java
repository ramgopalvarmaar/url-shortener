package com.url.shortener.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;

import com.url.shortener.controller.BaseTest;
import com.url.shortener.entity.UrlEntity;
import com.url.shortener.entity.UrlRepository;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.UrlNotFoundException;

public class UrlServiceTest extends BaseTest{
	
	@InjectMocks
    private UrlService urlService;
	
	@Mock
	UrlRepository urlRepository;
	
	@Mock
	UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void getUrl_returnsUrlFromDB() throws Exception {
    	String id = RandomStringUtils.randomAlphabetic(5);
    	String url = RandomStringUtils.randomAlphabetic(5);
    	
    	UrlEntity entity = new UrlEntity();
    	entity.setId(id);
    	entity.setUrl(url);
    	
    	when(urlRepository.findOne(id)).thenReturn(entity);
    	String returnedUrl = urlService.getUrl(id);
    	assertThat(returnedUrl, equalTo(url));
    	
    	verify(urlRepository).findOne(id);
    }
    
    @Test(expected = UrlNotFoundException.class)
    public void getUrl_throwsException_whenNoUrlFound() throws Exception {
    	String id = RandomStringUtils.randomAlphabetic(5);
    	when(urlRepository.findOne(id)).thenReturn(null);
    	
    	String returnedUrl = urlService.getUrl(id);
    	assertThat(returnedUrl, equalTo(null));
    	verify(urlRepository).findOne(id);
    }
    
    @Test
    public void getShortenedUrl_throwsException_whenNoUrlFound() throws Exception {
    	String url = "random";
    	
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setServerName("www.example.com");
    	request.setRequestURI("/foo");
    	
    	when(urlRepository.save(any(UrlEntity.class))).thenReturn(new UrlEntity());
    	
    	String returnedUrl = urlService.getShortenedUrl(url, request);
    	assertThat(returnedUrl, equalTo("http://www.example.com/shortener/98222e80"));
    	
    	verify(urlRepository).save(any(UrlEntity.class));
    }
    
    
}
