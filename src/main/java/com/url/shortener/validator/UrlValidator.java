package com.url.shortener.validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.url.shortener.entity.User;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.InvalidClientIdException;
import com.url.shortener.exception.InvalidUrlException;

@Component
public class UrlValidator {
	
	@Autowired
	UserRepository userRepository;
	
	public void validateUrl(String clientId, String url){
		User user = userRepository.findByApiKey(clientId);
		if (!isUrlValid(url)) {
			throw new InvalidUrlException("Invalid Url");
		} else if (user == null) {
			throw new InvalidClientIdException("Invalid client Id");
		}
	}
	
	private boolean isUrlValid(String url) {
		boolean valid = true;
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			valid = false;
		}
		return valid;
	}
}
