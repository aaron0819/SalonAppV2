package com.hampson.salonapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class BookNewAppointmentControllerTest {

	private MockMvc mockMvc;
	private InternalResourceViewResolver viewResolver;
	private BookNewAppointmentController controller;

	@Before
	public void setup() {
		controller = new BookNewAppointmentController();

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
		mockMvc.perform(get("/bookNewAppointment"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/templates/bookNewAppointment.html"))
				.andExpect(status().isOk());
	}
}
