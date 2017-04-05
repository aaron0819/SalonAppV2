package com.hampson.salonapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AccountService;

@Controller
public class CreateAccountController {

	private AccountService accountService;

	@RequestMapping("/createAccount")
	public ModelAndView createAccount(Model model, @RequestParam("emailAddress") String emailAddress,
			@RequestParam("password") String password, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("salonCode") String salonCode) {

		accountService = new AccountService();

		model.addAttribute("error",
				accountService.createAccount(emailAddress, password, firstName, lastName, phoneNumber, salonCode));

		return new ModelAndView("index");
	}
}
