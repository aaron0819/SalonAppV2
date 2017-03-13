package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		
		String page = "index";
		
		//Bypass login / registration page if active account already logged in 
		if(null != request.getSession().getAttribute("pageIndicator")) {
			page = "/login";
		}
		
		return new ModelAndView(page);
	}
}
