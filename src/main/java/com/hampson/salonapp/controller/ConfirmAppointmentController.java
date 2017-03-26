package com.hampson.salonapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.service.AppointmentService;

@Controller
public class ConfirmAppointmentController {

	@RequestMapping("/confirmAppointment")
	public ModelAndView confirmAppointment(Model model, HttpServletRequest request, @RequestParam("confirmedAppointmentId") String appointmentId) {
		
		String returnMessage = (String) new AppointmentService().confirmAppointment(Integer.parseInt(appointmentId));
		
		request.getSession().setAttribute("returnMessage", returnMessage);
		
		return new ModelAndView("redirect:/login");
	}
	
}
