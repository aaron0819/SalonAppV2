package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;
import com.hampson.salonapp.service.AppointmentService;

@Controller
public class LoginController {

	private AccountService accountService;
	private AppointmentService appointmentService;
	
	@RequestMapping("/login")
	public ModelAndView login(Model model, HttpServletRequest request, @RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password) {
		
		accountService = new AccountService();
		appointmentService = new AppointmentService();
		
		String page = "index";
		
		int pageIndicator = accountService.login(emailAddress, password, request);
		
		System.out.println(pageIndicator);
		
		if(1 == pageIndicator) {
			page = "customerHome";
		} else if(2 == pageIndicator){
			model.addAttribute("appointments", appointmentService.getAppointmentsByStylist((Integer) request.getSession().getAttribute("stylistId")));
			page = "stylistHome";
		} else {
			model.addAttribute("error", "Invalid Email Address and / or Password Entered");
			page = "index";
		}

		return new ModelAndView(page);
	}
}
