package com.url.shortener.login.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import com.url.shortener.controller.BaseTest;
import com.url.shortener.entity.User;
import com.url.shortener.login.service.UserProfileService;

public class LoginControllerTest extends BaseTest{
	
	private MockMvc mockMvc;

	@InjectMocks
    private LoginController loginController;

    @Mock
    UserProfileService userProfileService;
	
    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(loginController).build();
    }
    
    @Test
    public void signup_redirectsToSignUpPage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("signup"));
    }
    
    @Test
    public void createUser_createsNewUserInDB_returnsToSignUpPageWithSuccessMessage() throws Exception {
    	when(userProfileService.createUser(any(User.class))).thenReturn(new User());
        mockMvc.perform(post("/create")
        .param("userId", "exampleUser")
        .param("password", "*****"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("status", equalTo("Account created successfully")))
        .andExpect(view().name("signup"));
        
        verify(userProfileService).createUser(any(User.class));
    }
    
    @Test
    public void authenticate_validatesUserNameAndPasswordInDb_returnsToShowPageWithApiKey() throws Exception {
    	String apiKey = RandomStringUtils.randomAlphabetic(5);
    	User user = new User();
    	user.setApiKey(apiKey);
    	when(userProfileService.authenticate(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/authenticate")
        .param("userId", "exampleUser")
        .param("password", "*****"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("apiKey", equalTo(apiKey)))
        .andExpect(view().name("show"));
        
        verify(userProfileService).authenticate(any(User.class));
    }
    
    @Test
    public void getApiKey_returnsToShowPageWithApiKey() throws Exception {
    	Model model = mock(Model.class);
    	String apiKey = RandomStringUtils.randomAlphabetic(5);
    	User user = new User();
    	user.setApiKey(apiKey);
    	String viewName = loginController.getApiKey(user, model);
    	assertThat(viewName, equalTo("show"));
    }
    
}
