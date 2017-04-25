package com.hampson.salonapp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.email.EmailSender;
import com.hampson.salonapp.iface.AppointmentDAO;
import com.hampson.salonapp.jdbctemplate.AppointmentJDBCTemplate;
import com.hampson.salonapp.model.Appointment;
import com.hampson.salonapp.model.AppointmentRequest;
import com.hampson.salonapp.model.PendingAppointment;

public class AppointmentService {

	private AppointmentDAO appointmentJDBCTemplate;

	public AppointmentService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setAppointmentJDBCTemplate((AppointmentJDBCTemplate) context.getBean("appointmentJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}

	public void createAppointment(String appointmentType, String appointmentDate, String appointmentStartTime,
			String appointmentEndTime, int stylistId, long customerId) {
		getAppointmentJDBCTemplate().createAppointment(appointmentType, appointmentDate, appointmentStartTime,
				appointmentEndTime, stylistId, customerId);
	}

	public void requestAppointment(AppointmentRequest apptRequest) {
			int returnCode = getAppointmentJDBCTemplate().requestAppointment(apptRequest);
	
			String emailAddress = new AccountService().getCustomerEmailAddress(apptRequest.getCustomerId());
			
			if(0 == returnCode) {
				try {
					EmailSender.sendConfirmationEmail(emailAddress, apptRequest);
				} catch (IOException e) {
					//TODO - Email sending error handling logic
				}
			} else {
				//TODO - Request appointment error handling logic
			}
	}

	public List<Appointment> getAppointmentsByStylist(int stylistId) {
		return appointmentJDBCTemplate.getAppointmentsByStylistId(stylistId);
	}

	public List<Appointment> getAppointmentsByCustomerId(int customerId) {
		return appointmentJDBCTemplate.getAppointmentsByCustomerId(customerId);
	}

	public List<PendingAppointment> getPendingAppointmentsByStylistId(int stylistId) {
		return getAppointmentJDBCTemplate().getPendingAppointmentsByStylistId(stylistId);
	}
	
	public List<PendingAppointment> getPendingAppointmentsWithNoStylistPreference() {
		return getAppointmentJDBCTemplate().getPendingAppointmentsWithNoStylistPreference();
	}
	
	public Object getPendingAppointmentsByCustomerId(int customerId) {
		return getAppointmentJDBCTemplate().getPendingAppointmentsByCustomerId(customerId);
	}
	
	public String confirmAppointment(int pendingAppointmentId) {
		return getAppointmentJDBCTemplate().confirmAppointment(pendingAppointmentId);
	}
	
	public AppointmentDAO getAppointmentJDBCTemplate() {
		return appointmentJDBCTemplate;
	}

	public void setAppointmentJDBCTemplate(AppointmentDAO appointmentJDBCTemplate) {
		this.appointmentJDBCTemplate = appointmentJDBCTemplate;
	}
}
