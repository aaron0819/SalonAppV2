package com.hampson.salonapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hampson.salonapp.controller.IndexController;

public class IndexControllerTest {

	private IndexController controller;
	private HttpServletRequest request;
	private HttpSession session;

	@Before
	public void setUp() throws Exception {
		controller = new IndexController();
		setUpMockRequestAndResponse();
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	/*
	 * When the page indicator is not set in the session, the expected behavior
	 * is the controller will return a ModelAndView with a view name of index
	 */
	@Test
	public void testIndexPageIndicatorNotSet() {

		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("pageIndicator")).thenReturn(null);

		String expectedViewName = "index";
		String actualViewName = controller.index(request).getViewName();

		assertEquals(expectedViewName, actualViewName);
	}
	
	/*
	 * When the page indicator is not set as an empty string in the session, the expected behavior
	 * is the controller will return a ModelAndView with a view name of index
	 */
	@Test
	public void testIndexPageIndicatorEmptyString() {

		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("pageIndicator")).thenReturn("");

		String expectedViewName = "index";
		String actualViewName = controller.index(request).getViewName();

		assertEquals(expectedViewName, actualViewName);
	}

	/*
	 * When the page indicator is set as a valid integer indicator value in the session, the expected behavior
	 * is the controller will return a ModelAndView with a redirect to the login controller to bypass the login screen
	 */
	@Test
	public void testIndexPageIndicatorValidIndicator() {

		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("pageIndicator")).thenReturn(1);

		String expectedViewName = "redirect:/login";
		String actualViewName = controller.index(request).getViewName();

		assertEquals(expectedViewName, actualViewName);
	}
	
	/*
	 * When the page indicator is set as a valid integer value in the session but not a valid indicator, the expected behavior
	 * is the controller will return a ModelAndView with a redirect to the login controller to bypass the login screen
	 */
	@Test
	public void testIndexPageIndicatorValidIntegerNotIndicator() {

		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("pageIndicator")).thenReturn(909090909);

		String expectedViewName = "index";
		String actualViewName = controller.index(request).getViewName();

		assertEquals(expectedViewName, actualViewName);
	}
	
	private void setUpMockRequestAndResponse() {
		request = mock(HttpServletRequest.class);
		session = mock(HttpSession.class);
	}
}
