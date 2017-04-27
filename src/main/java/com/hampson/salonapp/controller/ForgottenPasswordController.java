package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForgottenPasswordController {

	@RequestMapping("/forgottenPassword")
	public ModelAndView forgottenPassword() {
		return new ModelAndView("forgottenPassword");
	}
	
	@RequestMapping("/sendRecoveryEmail")
	public ModelAndView sendRecoveryEmail() {
		ModelAndView mav = new ModelAndView("index");
		
		mav.addObject("error", "Recovery Email Sent");
		
		return mav;
	}
	
}
