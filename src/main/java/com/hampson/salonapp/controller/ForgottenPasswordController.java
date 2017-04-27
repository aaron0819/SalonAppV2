package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;

@Controller
public class ForgottenPasswordController {

	@RequestMapping("/forgottenPassword")
	public ModelAndView forgottenPassword() {
		return new ModelAndView("forgottenPassword");
	}
	
	@RequestMapping("/sendRecoveryEmail")
	public ModelAndView sendRecoveryEmail(@RequestParam("emailAddress") String emailAddress) {
		ModelAndView mav = new ModelAndView("index");
		
		new AccountService().resetPassword(emailAddress);
		
		mav.addObject("error", "Recovery Email Sent");
		
		return mav;
	}
	
}
