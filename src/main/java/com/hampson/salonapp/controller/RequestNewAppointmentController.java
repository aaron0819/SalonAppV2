package com.hampson.salonapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.model.Service;
import com.hampson.salonapp.model.Stylist;
import com.hampson.salonapp.service.ServiceService;
import com.hampson.salonapp.service.StylistService;

@Controller
public class RequestNewAppointmentController {

	@RequestMapping("/requestNewAppointment")
	public ModelAndView requestNewAppointment() {
		
		ModelAndView mav = new ModelAndView();
		StylistService stylistService = new StylistService();
		ServiceService serviceService = new ServiceService();
		
		List<Stylist> stylists = stylistService.getAllStylists();
		List<Service> services = serviceService.getAllServices();
		
		mav.addObject("stylists", stylists);
		mav.addObject("services", services);
		
		mav.setViewName("requestNewAppointment");
		
		return mav;
	}
	
}
