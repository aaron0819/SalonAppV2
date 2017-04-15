package com.hampson.salonapp.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class LogoutControllerTest {
	private MockMvc mockMvc;
	private InternalResourceViewResolver viewResolver;
	private LogoutController controller;
	private Map<String, Object> sessionAttributes;
	private HttpServletRequest request;
	private HttpSession session;

	@Before
	public void setup() {
		controller = new LogoutController();

		viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();

		sessionAttributes = new HashMap<String, Object>();
		sessionAttributes.put("pageIndicator", 1);
		sessionAttributes.put("customerId", 22);
		sessionAttributes.put("stylistId", 0);
		
		request = mock(HttpServletRequest.class);
		session = mock(HttpSession.class);
	}

	@After
	public void tearDown() {
		controller = null;
		mockMvc = null;
		viewResolver = null;
		sessionAttributes = null;
	}

	@Test
	public void getHappyPathRequest() throws Exception {
		mockMvc.perform(get("/logout").sessionAttrs(sessionAttributes)).andExpect(forwardedUrl("/templates/index.html"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSessionAttributeCalls() {
		when(request.getSession()).thenReturn(session);
		
		controller.logout(request);
		
		verify(request, times(3)).getSession();
		verify(session, times(1)).setAttribute("pageIndicator", null);
		verify(session, times(1)).setAttribute("customerId", null);
		verify(session, times(1)).setAttribute("stylistId", null);
	}
}
