package com.hampson.salonapp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServletInitializerTest {

	private ServletInitializer initializer;
	
	@Before
	public void setup() {
		initializer = new ServletInitializer();
	}
	
	@After
	public void tearDown() {
		initializer = null;
	}
	
	@Test
	public void testConfigure() {
		assertEquals(initializer.configure(new SpringApplicationBuilder(new Object[] {})).getClass(), SpringApplicationBuilder.class);
	}
	
}
