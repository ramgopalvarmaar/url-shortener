package com.url.shortener.controller;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class BaseTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
}
