package com.hampson.salonapp.controller;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.hampson.salonapp.model.Service;

public class OurServicesControllerTest {

	private MockMvc mockMvc;
	private InternalResourceViewResolver viewResolver;
	private OurServicesController controller;

	@Before
	public void setup() {
		controller = new OurServicesController();

		viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");

		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testHappyPathRequest() throws Exception {
		mockMvc.perform(get("/ourServices")).andExpect(forwardedUrl("/templates/ourServices.html"))
				.andExpect(status().isOk()).andExpect(view().name("ourServices"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void verifyModelAndView() {
		ModelAndView actual = controller.ourServices();
		assertNotEquals(((List<Service> ) actual.getModel().get("services")).size(), 0);
	}
}
