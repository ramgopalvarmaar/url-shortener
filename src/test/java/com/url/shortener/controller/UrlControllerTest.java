package com.url.shortener.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.url.shortener.service.UrlService;
import com.url.shortener.validator.UrlValidator;

public class UrlControllerTest extends BaseTest{
	
	private MockMvc mockMvc;

	@InjectMocks
    private UrlController urlController;

    @Mock
	UrlService urlService;
	
    @Mock
	UrlValidator urlValidator;
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(urlController).build();
    }
    
    @Test
    public void redirectToUrl_redirectsUserToTheActualUrl() throws Exception {
    	String url = RandomStringUtils.randomAlphabetic(10);
        when(urlService.getUrl("abc")).thenReturn(url);

        mockMvc.perform(get("/shortener/abc")).andExpect(status().is3xxRedirection()).andExpect(header().string("Location", equalTo(url)));
        verify(urlService).getUrl("abc");
    }
    
    @Test
    public void shortenUrl_shortensTheUrlAndStoresInDb() throws Exception {
    	String shortenedUrl = RandomStringUtils.randomAlphabetic(5);
    	String url = RandomStringUtils.randomAlphabetic(10);
    	String cliendId = RandomStringUtils.randomAlphabetic(10);
        when(urlService.getShortenedUrl(any(String.class), any(HttpServletRequest.class))).thenReturn(shortenedUrl);
        doNothing().when(urlValidator).validateUrl(cliendId, url);

        mockMvc.perform(post("/shortener/")
        .header("X-Client-Id", cliendId)
        .param("url",url)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shortenedUrl", is(shortenedUrl)))
        .andExpect(jsonPath("$.message", is("SUCCESS")));
        
        verify(urlValidator).validateUrl(cliendId,url);
        verify(urlService).getShortenedUrl(any(String.class), any(HttpServletRequest.class));
    }
}
