package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestNewAppointmentController {

	@RequestMapping("/requestNewAppointment")
	public ModelAndView requestNewAppointment() {
		
		return new ModelAndView("requestNewAppointment");
	}
}
