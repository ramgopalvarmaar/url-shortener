package com.url.shortener.controller.model;

public class ShortenUrlResponse {
	
	private String shortenedUrl;
	private String message;
	
	public ShortenUrlResponse(String shortenedUrl, String message) {
		super();
		this.shortenedUrl = shortenedUrl;
		this.message = message;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
