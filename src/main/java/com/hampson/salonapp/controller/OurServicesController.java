package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.ServiceService;

@Controller
public class OurServicesController {

	@RequestMapping("/ourServices") 
	public ModelAndView ourServices(Model model) {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("services", new ServiceService().getAllServices());
		
		return mav;		
	}
}