package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;

@Controller
public class VerificationController {

	@RequestMapping("/verify")
	public ModelAndView verifyAccount(@RequestParam("verificationCode") String verificationCode,
			@RequestParam("emailAddress") String emailAddress, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/login");
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
					System.out.println("customer");
				} else if (Integer.parseInt(account[1]) > 0) {
					request.getSession().setAttribute("pageIndicator", 2);
					request.getSession().setAttribute("stylistId", Integer.parseInt(account[1]));
					System.out.println("stylist");
				}
			}
		}

		//mav.addObject("returnMessage", returnMessage);

		return mav;
	}

}
