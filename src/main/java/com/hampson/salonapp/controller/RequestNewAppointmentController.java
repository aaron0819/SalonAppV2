package com.hampson.salonapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.model.Stylist;
import com.hampson.salonapp.service.StylistService;

@Controller
public class RequestNewAppointmentController {

	@RequestMapping("/requestNewAppointment")
	public ModelAndView requestNewAppointment() {
		
		ModelAndView mav = new ModelAndView();
		StylistService service = new StylistService();
		
		List<Stylist> stylists = service.getAllStylists();
		
		mav.addObject("stylists", stylists);
		mav.setViewName("requestNewAppointment");
		
		return mav;
	}
	
}
