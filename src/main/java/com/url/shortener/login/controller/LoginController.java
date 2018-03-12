package com.url.shortener.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.url.shortener.entity.User;
import com.url.shortener.login.service.UserProfileService;

@Controller
public class LoginController {
	
	private static final String API_KEY = "apiKey";
	private static final String STATUS = "status";
	
	@Autowired
	UserProfileService userProfileService;

	@RequestMapping(method=RequestMethod.GET)
    public String signup() {
        return "signup"; 
    }
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
    public String createUser(User user, Model model) {
        userProfileService.createUser(user);
		model.addAttribute(STATUS, "Account created successfully");
        return "signup";
    }
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public String authenticate(User user, Model model) {
		User authenticatedUser = userProfileService.authenticate(user);
    	model.addAttribute(API_KEY, authenticatedUser.getApiKey());
        return "show";
	}
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
    public String getApiKey(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute(API_KEY, user.getApiKey());
        return "show";
    }
}
