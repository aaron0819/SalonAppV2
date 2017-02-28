package com.hampson.salonapp.service;

import static java.lang.Integer.parseInt;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.iface.AppointmentDAO;
import com.hampson.salonapp.jdbctemplate.AppointmentJDBCTemplate;
import com.hampson.salonapp.model.Appointment;

public class AppointmentService {

	private AppointmentDAO appointmentJDBCTemplate;
	
	public AppointmentService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setAppointmentJDBCTemplate((AppointmentJDBCTemplate) context.getBean("appointmentJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}
	
	public List<Appointment> getAppointmentsByStylist(int stylistId) {
		return appointmentJDBCTemplate.getAppointmentsByStylistId(stylistId);
	}

	public AppointmentDAO getAppointmentJDBCTemplate() {
		return appointmentJDBCTemplate;
	}

	public void setAppointmentJDBCTemplate(AppointmentDAO appointmentJDBCTemplate) {
		this.appointmentJDBCTemplate = appointmentJDBCTemplate;
	}

}
