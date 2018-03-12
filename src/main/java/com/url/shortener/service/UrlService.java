package com.url.shortener.service;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;
import com.url.shortener.entity.UrlEntity;
import com.url.shortener.entity.UrlRepository;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.UrlNotFoundException;

@Component
public class UrlService {

	@Autowired
	UrlRepository urlRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public String getUrl(String id){
		final String url;
		UrlEntity entity = urlRepository.findOne(id);
		if (entity == null) {
			throw new UrlNotFoundException();
		}else {
			url = entity.getUrl();
		}
		return url;
	}

	public String getShortenedUrl(String url, HttpServletRequest httpRequest) {
		final String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
		UrlEntity entity = new UrlEntity();
    	entity.setId(id);
    	entity.setUrl(url);
    	urlRepository.save(entity);
    	
    	String requestUrl = httpRequest.getRequestURL().toString();
		String prefix = requestUrl.substring(0,
				requestUrl.indexOf(httpRequest.getRequestURI(), "http://".length()));
		
		return prefix + "/shortener/" + id;
	}
	
	
}
