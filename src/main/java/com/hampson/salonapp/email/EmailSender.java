package com.hampson.salonapp.email;

import static com.hampson.salonapp.constants.Constants.APPOINTMENT_CONFIRMATION_EMAIL_SUBJECT;
import static com.hampson.salonapp.constants.Constants.APPOINTMENT_REQUEST_CONFIRMATION_EMAIL_SUBJECT;
import static com.hampson.salonapp.constants.Constants.FROM_EMAIL_ADDRESS;
import static com.hampson.salonapp.constants.Constants.PASSWORD_RESET_EMAIL_SUBJECT;
import static com.hampson.salonapp.constants.Constants.VERIFICATION_CODE_URL;
import static com.hampson.salonapp.constants.Constants.VERIFICATION_EMAIL_SUBJECT;
import static com.sendgrid.Method.POST;
import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;

import java.io.IOException;

import com.hampson.salonapp.model.AppointmentRequest;
import com.hampson.salonapp.model.PendingAppointment;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

public class EmailSender {

	public static void sendVerificationEmail(String userEmail, String verificationCode) throws IOException {
		Email from = new Email(FROM_EMAIL_ADDRESS);
		Email to = new Email(userEmail);
		Content content = new Content("text/html",
				"Hello, <br />Thank you for signing up for a Salon Appointntment Manager account. Below is the link to activate your account. <br /><br /><a href="
						+ VERIFICATION_CODE_URL + verificationCode + "&emailAddress=" + userEmail
						+ "\">Verify</a><br/><br/>Thank you,<br />Salon Appointment Manager Staff");
		Mail mail = new Mail(from, VERIFICATION_EMAIL_SUBJECT, to, content);

		Request request = new Request();
		try {
			request.method = POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			new SendGrid(getenv("SENDGRID_API_KEY")).api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static void sendRequestConfirmationEmail(String emailAddress, AppointmentRequest apptRequest)
			throws IOException {
		Email from = new Email(FROM_EMAIL_ADDRESS);
		Email to = new Email(emailAddress);
		Content content = new Content("text/html", "Thank you for requesting an appointment with Salon. "
				+ "This is a confirmation that we have recieved your appointment request. "
				+ "<br/>Your appointment request details are as follows:<br/>" + "Appointment Type: "
				+ apptRequest.getAppointmentType() + "<br/>Appointment Date: " + apptRequest.getAppointmentDate()
				+ "<br/>Appointment Time: " + apptRequest.getAppointmentStartTime() + "<br/>Stylist: "
				+ (parseInt(apptRequest.getPreferredStylist()) == 0 ? "No Preferred Stylist Indicated" : ""));

		Mail mail = new Mail(from, APPOINTMENT_REQUEST_CONFIRMATION_EMAIL_SUBJECT, to, content);

		Request request = new Request();
		try {
			request.method = POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			new SendGrid(getenv("SENDGRID_API_KEY")).api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static void sendConfirmedEmail(PendingAppointment pendingAppt) throws IOException {
		Email from = new Email(FROM_EMAIL_ADDRESS);
		Email to = new Email(pendingAppt.getCustomer().getEmailAddress());
		Content content = new Content("text/html", "Hello, <br/>Your appointment request has been confirmed for "
				+ pendingAppt.getRequestedDate() + " at " + pendingAppt.getRequestedTime());
		Mail mail = new Mail(from, APPOINTMENT_CONFIRMATION_EMAIL_SUBJECT, to, content);

		Request request = new Request();
		try {
			request.method = POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			new SendGrid(getenv("SENDGRID_API_KEY")).api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	/**
	 * This method will send a password reset email to the email address entered
	 * on the forgotten password page
	 * 
	 * @param emailAddress
	 *            - Email address of account where password needs to be reset
	 * @param tempPassword
	 *            - Generated temporary password to access account to allow for
	 *            new password to be set
	 * @throws IOException
	 */
	public static void sendPasswordResetEmail(String emailAddress, String tempPassword) throws IOException {
		Email from = new Email(FROM_EMAIL_ADDRESS);
		Email to = new Email(emailAddress);
		Content content = new Content("text/html",
				"Hello, <br/>We have recieved a request to reset your password. <br/>Your temporary password is: "
						+ tempPassword + "<br/>Thank you, <br/> Salon Appointment Manager Staff");
		Mail mail = new Mail(from, PASSWORD_RESET_EMAIL_SUBJECT, to, content);

		SendGrid sg = new SendGrid(getenv("SENDGRID_API_KEY"));
		Request request = new Request();
		try {
			request.method = POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}
}