package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.constants.PageIndicators;

/**
 * Controller class for index.html. Will direct to login page if user does not
 * have an active login session else will redirect to login controller
 * 
 * @author Aaron D. Hampson
 * @version 1.0 - 03/30/2017
 *
 */
@Controller
public class IndexController {

	/**
	 * @param request - HttpServletRequest request input
	 * @return ModelAndView containing a view name or a redirect
	 */
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request) {

		String page = "index";
		
		Object pageIndicator = request.getSession().getAttribute("pageIndicator");
		// Bypass login / registration page if active account already logged in
		if (isValidPageIndicator(pageIndicator)) {
			page = "redirect:/login";
		}

		return new ModelAndView(page);
	}

	/**
	 * Validates the page indicator in the session is a valid value
	 * 
	 * @param pageIndicator
	 *            - Indicator based on account type to load proper view for user
	 *            role upon logging in
	 */
	private boolean isValidPageIndicator(Object pageIndicator) {
		boolean isValid = false;

		try {
			for (PageIndicators indicator : PageIndicators.values()) {
				if ((int) pageIndicator == indicator.getValue()) {
					isValid = true;
					break;
				}
			}
		} catch (ClassCastException cce) {
			isValid = false;
		} catch (NullPointerException npe) {
			isValid = false;
		}

		return isValid;
	}
}
