package com.hampson.salonapp.email;

import java.io.IOException;

import com.hampson.salonapp.model.AppointmentRequest;
import com.hampson.salonapp.service.CustomerService;
// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class EmailSender {

	public static void sendVerificationEmail(String userEmail, String verificationCode) throws IOException {
		Email from = new Email("aaron.d.hampson@gmail.com");
		String subject = "Salon Appointment Manager Account Verification Code";
		Email to = new Email(userEmail);
		Content content = new Content("text/html",
				"Hello, <br />Thank you for signing up for a Salon Appointntment Manager account. Below is the link to activate your account. <br /><br /><a href=\"https://salonapp-springboot.herokuapp.com/verify?verificationCode="
						+ verificationCode + "&emailAddress=" + userEmail
						+ "\">Verify</a><br/><br/>Thank you,<br />Salon Appointment Manager Staff");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static void sendConfirmationEmail(String emailAddress, AppointmentRequest apptRequest) throws IOException {
		Email from = new Email("aaron.d.hampson@gmail.com");
		String subject = "Salon Appointment Manager Account Verification Code";
		Email to = new Email(emailAddress);
		Content content = new Content("text/html", "Thank you for requesting an appointment with Salon. "
				+ "This is a confirmation that we have recieved your appointment request. "
				+ "<br/>Your appointment request details are as follows:<br/>" + "Appointment Type: "
				+ apptRequest.getAppointmentType() + "<br/>Appointment Date: " + apptRequest.getAppointmentDate()
				+ "<br/>Appointment Time: " + apptRequest.getAppointmentStartTime()
				+ "<br/>Stylist: " + apptRequest.getPreferredStylist());
		// TODO
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}
}