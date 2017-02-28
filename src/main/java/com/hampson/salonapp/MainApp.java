/*package com.hampson.salonapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.jdbctemplate.AccountJDBCTemplate;
import com.hampson.salonapp.service.LoginService;

public class MainApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

		// AppointmentJDBCTemplate appointmentJDBCTemplate =
		// (AppointmentJDBCTemplate) context.getBean("appointmentJDBCTemplate");
		AccountJDBCTemplate accountJDBCTemplate = (AccountJDBCTemplate) context.getBean("accountJDBCTemplate");

		((ConfigurableApplicationContext) context).close();

		
		 * //appointmentJDBCTemplate.createAppointment("Hair Apt", "02-23-2017",
		 * "03:00", "04:00", 1, 1);
		 * System.out.println(appointmentJDBCTemplate.getAppointment(2).toString
		 * ()); for(Appointment a :
		 * appointmentJDBCTemplate.getAppointmentsByStylistId(1)) {
		 * System.out.println(a.toString()); }
		 * 
		 * appointmentJDBCTemplate.updateAppointment("service", "01-01-2017",
		 * "03:00", "04:00", 1);
		 * 
		 * for(Appointment a :
		 * appointmentJDBCTemplate.getAppointmentsByCustomerId(1)) {
		 * System.out.println(a.toString()); }
		 * 
		 * //appointmentJDBCTemplate.deleteAppointment(3);
		 * 
		 
			
		//accountJDBCTemplate.createAccount("test@gmail.com", "1", "first", "last", "555-555-3552", 0);
		
		new LoginService().validateCredentials("test@gmail.com", "1");
	}
}
*/