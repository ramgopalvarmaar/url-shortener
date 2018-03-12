package com.url.shortener.login.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.url.shortener.controller.BaseTest;
import com.url.shortener.entity.User;
import com.url.shortener.entity.UserRepository;
import com.url.shortener.exception.UserUnAuthorizedException;

public class UserProfileServiceTest extends BaseTest{
	
	@InjectMocks
    private UserProfileService userProfileService;
	
	@Mock
	private UserRepository userRepository;

    @Test
    public void createUser_savesUserDetailsInDB() throws Exception {
    	User newUser = new User();
    	newUser.setUserId("exampleUser");
    	newUser.setPassword("***");
    	
    	User createdUser = newUser;
    	
    	when(userRepository.save(newUser)).thenReturn(createdUser);
    	User returnedUser = userProfileService.createUser(newUser);
    	assertThat(returnedUser.getUserId(), equalTo("exampleUser"));
    	assertThat(returnedUser.getPassword(), equalTo("***"));
    	
    	verify(userRepository).save(newUser);
    }
    
    @Test
    public void authenticate_authenticatesUserAgainstDB() throws Exception {
    	User authenticatedUser = new User();
    	authenticatedUser.setUserId("exampleUser");
    	authenticatedUser.setPassword("***");
    	
    	User user = authenticatedUser;
    	
    	when(userRepository.findByUserIdAndPassword("exampleUser", "***")).thenReturn(authenticatedUser);
    	
    	User returnedUser = userProfileService.authenticate(user);
    	assertThat(returnedUser.getUserId(), equalTo("exampleUser"));
    	assertThat(returnedUser.getPassword(), equalTo("***"));
    	
    	verify(userRepository).findByUserIdAndPassword("exampleUser", "***");
    }
    
    
    @Test(expected = UserUnAuthorizedException.class)
    public void authenticate_whenNoUserPresent_throwsException() throws Exception {
    	User user = new User();
    	when(userRepository.findByUserIdAndPassword("exampleUser", "***")).thenReturn(null);
    	userProfileService.authenticate(user);
    	verify(userRepository).findByUserIdAndPassword("exampleUser", "***");
    }
    
}
