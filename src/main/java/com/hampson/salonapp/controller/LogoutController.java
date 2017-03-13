package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		
		request.getSession().setAttribute("pageIndicator", null);
		request.getSession().setAttribute("customerId", null);
		request.getSession().setAttribute("stylistId", null);
		
		return new ModelAndView("index");
	}
	
}
