package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;
import com.hampson.salonapp.service.AppointmentService;

@Controller
public class VerificationController {

	private AppointmentService appointmentService;

	@RequestMapping("/verify")
	public ModelAndView verifyAccount(@RequestParam("verificationCode") String verificationCode,
			@RequestParam("emailAddress") String emailAddress, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		AccountService accountService = new AccountService();

		String[] account = accountService.retrieveAccountByEmailAddress(emailAddress);
		String returnMessage = "Invalid verification code or this code has already been redeemed";

		if (null != account) {
			if (account[2] != null && account[2].equalsIgnoreCase(verificationCode)) {
				accountService.verifyAccount(emailAddress);
				returnMessage = "Account Successfully Verified";

				if (Integer.parseInt(account[0]) > 0) {
					request.getSession().setAttribute("pageIndicator", 1);
					request.getSession().setAttribute("customerId", Integer.parseInt(account[0]));
					mav = new ModelAndView("customerHome");
					mav.addObject("appointments", appointmentService
							.getAppointmentsByCustomerId((Integer) request.getSession().getAttribute("customerId")));
					mav.addObject("pendingAppointments", appointmentService.getPendingAppointmentsByCustomerId((Integer) request.getSession().getAttribute("customerId")));

				} else if (Integer.parseInt(account[1]) > 0) {
					request.getSession().setAttribute("pageIndicator", 2);
					request.getSession().setAttribute("stylistId", Integer.parseInt(account[1]));
					mav = new ModelAndView("stylistHome");		
					mav.addObject("appointments", appointmentService
							.getAppointmentsByStylist((Integer) request.getSession().getAttribute("stylistId")));
					mav.addObject("myPendingAppointments", appointmentService.getPendingAppointmentsByStylistId((Integer) request.getSession().getAttribute("stylistId")));
					mav.addObject("noPreferencePendingAppointments", appointmentService.getPendingAppointmentsWithNoStylistPreference());
				}
			}
		}

		//mav.addObject("returnMessage", returnMessage);

		return mav;
	}

}
