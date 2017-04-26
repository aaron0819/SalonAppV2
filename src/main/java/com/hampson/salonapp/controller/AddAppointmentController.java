package com.hampson.salonapp.controller;

import static com.hampson.salonapp.util.AppointmentUtils.buildAppointmentObject;
import static java.lang.Integer.parseInt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hampson.salonapp.model.AppointmentRequest;
import com.hampson.salonapp.model.Customer;
import com.hampson.salonapp.model.Stylist;
import com.hampson.salonapp.service.AppointmentService;
import com.hampson.salonapp.service.CustomerService;
import com.hampson.salonapp.service.StylistService;

@Controller
public class AddAppointmentController {

	@RequestMapping("/stylistAddAppointment")
	public ModelAndView addEvent(HttpServletRequest request, @RequestParam("appointmentType") String appointmentType,
			@RequestParam("customerFirstName") String customerFirstName,
			@RequestParam("customerLastName") String customerLastName,
			@RequestParam("customerPhoneNumber") String customerPhoneNumber,
			@RequestParam("appointmentDate") String appointmentDate,
			@RequestParam("appointmentStartTime") String appointmentStartTime,
			@RequestParam("appointmentEndTime") String appointmentEndTime) throws IOException {

		CustomerService customerService = new CustomerService();
		AppointmentService appointmentService = new AppointmentService();

		Customer c = customerService.getCustomer(customerFirstName, customerLastName, customerPhoneNumber);

		if (null != c) {
			appointmentService.createAppointment(appointmentType, appointmentDate.toString(),
					appointmentStartTime.toString(), appointmentEndTime.toString(),
					(int) request.getSession().getAttribute("stylistId"), c.getId());
		} else {
			long customerId = customerService.createCustomer(customerFirstName, customerLastName, customerPhoneNumber);

			appointmentService.getAppointmentJDBCTemplate().createAppointment(appointmentType,
					appointmentDate.toString(), appointmentStartTime.toString(), appointmentEndTime.toString(),
					(int) request.getSession().getAttribute("stylistId"), customerId);
		}

		return new ModelAndView("redirect:/login");
	}

	@RequestMapping("/customerRequestAppointment")
	public ModelAndView submitAppointmentRequest(HttpServletRequest request,
			@RequestParam("appointmentType") String appointmentType,
			@RequestParam("appointmentDate") String appointmentDate,
			@RequestParam("alternateAppointmentDate") String alternateAppointmentDate,
			@RequestParam("appointmentStartTime") String appointmentStartTime,
			@RequestParam("alternateAppointmentTime") String alternateAppointmentTime,
			@RequestParam("preferredStylist") String preferredStylist) {

		AppointmentService appointmentService = new AppointmentService();
		StylistService stylistService = new StylistService();
		
		Stylist stylist = stylistService.getStylistById(parseInt(preferredStylist));
		
		AppointmentRequest appt = buildAppointmentObject(appointmentType, (int) request.getSession().getAttribute("customerId"),
				appointmentDate, alternateAppointmentDate, appointmentStartTime, alternateAppointmentTime, stylist.getFirstName());
		
		appointmentService.requestAppointment(appt);

		return new ModelAndView("redirect:/login");
	}
}
