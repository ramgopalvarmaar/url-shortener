package com.url.shortener.login.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.shortener.entity.User;
import com.url.shortener.entity.UserLoginType;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.UserUnAuthorizedException;
import com.url.shortener.utility.ApiKeyGenerator;

@Service
public class UserProfileService {

	@Autowired
	UserRepository userRepository;
	
	public User createUser(User user) {
		user.setApiKey(ApiKeyGenerator.generate(128));
		user.setLoginType(UserLoginType.APPLICATION);
        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
	}

	public User authenticate(User user) {
		User authenticatedUser = userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		if(authenticatedUser == null) {
			throw new UserUnAuthorizedException("Invalid User");
	    }
		return authenticatedUser;
	}

}
