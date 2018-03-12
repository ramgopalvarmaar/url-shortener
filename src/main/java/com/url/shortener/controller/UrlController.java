package com.url.shortener.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortener.controller.model.ShortenUrlRequest;
import com.url.shortener.controller.model.ShortenUrlResponse;
import com.url.shortener.service.UrlService;
import com.url.shortener.validator.UrlValidator;

@RestController
@RequestMapping("shortener")
public class UrlController {

	@Autowired
	UrlService urlService;
	
	@Autowired
	UrlValidator urlValidator;

	@GetMapping(value = "/{id}")
	public void redirectToUrl(@PathVariable String id, HttpServletResponse resp) throws Exception {
		resp.addHeader("Location", urlService.getUrl(id));
		resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	}

	@PostMapping()
	public ShortenUrlResponse shortenUrl(@Valid ShortenUrlRequest request,
			@RequestHeader("X-Client-Id") String cliendId, HttpServletRequest httpRequest) {
		String url = request.getUrl();
		urlValidator.validateUrl(cliendId, url);
		return new ShortenUrlResponse(urlService.getShortenedUrl(url, httpRequest),"SUCCESS");
	}
}
