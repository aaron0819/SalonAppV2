package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;
import com.hampson.salonapp.service.AppointmentService;

@Controller
public class LoginController {

	private AccountService accountService;
	private AppointmentService appointmentService;

	@RequestMapping("/login")
	public ModelAndView login(Model model, HttpServletRequest request) {

		String page = "index";
		int pageIndicator = 0;

		if (null == request.getSession().getAttribute("pageIndicator")) {
			accountService = new AccountService();
			appointmentService = new AppointmentService();

			String emailAddress = request.getParameter("emailAddress");
			String password = request.getParameter("password");

			pageIndicator = accountService.login(emailAddress, password, request);
		} else {
			pageIndicator = (int) request.getSession().getAttribute("pageIndicator");
		}
		if (1 == pageIndicator) {
			model.addAttribute("appointments", appointmentService
					.getAppointmentsByCustomerId((Integer) request.getSession().getAttribute("customerId")));
			page = "customerHome";
		} else if (2 == pageIndicator) {
			model.addAttribute("appointments", appointmentService
					.getAppointmentsByStylist((Integer) request.getSession().getAttribute("stylistId")));
			model.addAttribute("myPendingAppointments", appointmentService.getPendingAppointmentsByStylistId((Integer) request.getSession().getAttribute("stylistId")));
			model.addAttribute("noPreferencePendingAppointments", appointmentService.getPendingAppointmentsWithNoStylistPreference());
			page = "stylistHome";
		} else {
			model.addAttribute("error", "Invalid Email Address and / or Password Entered");
			page = "index";
		}

		request.getSession().setAttribute("pageIndicator", pageIndicator);

		return new ModelAndView(page);
	}
}
