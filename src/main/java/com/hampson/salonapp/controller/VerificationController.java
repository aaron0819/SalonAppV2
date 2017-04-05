package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VerificationController {

	@RequestMapping
	public ModelAndView verifyAccount(@RequestParam("verificationCode") String verificationCode) {
		
		
		
		return new ModelAndView();
	}
	
}
