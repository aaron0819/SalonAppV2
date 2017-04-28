package com.hampson.salonapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class RequestNewAppointmentControllerTest {

	private MockMvc mockMvc;
	private InternalResourceViewResolver viewResolver;
	private RequestNewAppointmentController controller;

	@Before
	public void setup() {
		controller = new RequestNewAppointmentController();

		viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() {
		mockMvc = null;
	}
	
	@Test
	public void getHappyPathRequest() throws Exception {
		mockMvc.perform(get("/requestNewAppointment"))
				.andExpect(forwardedUrl("/templates/requestNewAppointment.html"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("stylists"))
				.andExpect(model().attributeExists("services"))
				.andExpect(view().name("requestNewAppointment"));
	}

}
