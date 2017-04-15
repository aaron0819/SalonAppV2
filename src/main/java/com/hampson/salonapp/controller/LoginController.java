package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;
import com.hampson.salonapp.service.AppointmentService;

@Controller
public class LoginController {

	private AccountService accountService;
	private AppointmentService appointmentService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String page = "index";

		int pageIndicator = 0;
		accountService = new AccountService();
		appointmentService = new AppointmentService();
		if (null == request.getSession().getAttribute("pageIndicator")) {

			String emailAddress = request.getParameter("emailAddress");
			String password = request.getParameter("password");

			pageIndicator = accountService.login(emailAddress, password, request);
		} else {
			pageIndicator = (int) request.getSession().getAttribute("pageIndicator");
		}

		if (1 == pageIndicator) {
			mav.addObject("appointments", appointmentService
					.getAppointmentsByCustomerId((Integer) request.getSession().getAttribute("customerId")));
			mav.addObject("pendingAppointments", appointmentService
					.getPendingAppointmentsByCustomerId((Integer) request.getSession().getAttribute("customerId")));
			page = "customerHome";
		} else if (2 == pageIndicator) {
			mav.addObject("appointments", appointmentService
					.getAppointmentsByStylist((Integer) request.getSession().getAttribute("stylistId")));
			mav.addObject("myPendingAppointments", appointmentService
					.getPendingAppointmentsByStylistId((Integer) request.getSession().getAttribute("stylistId")));
			mav.addObject("noPreferencePendingAppointments",
					appointmentService.getPendingAppointmentsWithNoStylistPreference());
			page = "stylistHome";
		} else if(3 == pageIndicator) {
			mav.addObject("error", "Account has not been verified. Please use the activation link in the email sent to the account you registered with to activate your account");
		} else {
			mav.addObject("error", "Invalid Email Address and / or Password Entered");
			page = "index";
		}

		mav.addObject("alertMessage", request.getSession().getAttribute("returnMessage"));
		request.getSession().setAttribute("returnMessage", "");

		request.getSession().setAttribute("pageIndicator", pageIndicator);

		mav.setViewName(page);
		return mav;// new ModelAndView(page);
	}
}
