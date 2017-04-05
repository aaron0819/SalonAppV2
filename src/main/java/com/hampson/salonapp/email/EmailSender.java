package com.hampson.salonapp.email;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

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

	public static void main(String[] args) throws IOException {
		sendEmail("aaron.d.hampson@gmail.com", "test");
	}

	public static void sendEmail(String userEmail, String verificationCode) throws IOException {

		Email from = new Email("aaron.d.hampson@gmail.com");
		String subject = "Salon Appointment Manager Account Verification Code";
		Email to = new Email(userEmail);
		Content content = new Content("text/html",
				"Hello, <br />Thank you for signing up for a Salon Appointntment Manager account. Below is the link to activate your account. <br /><br /><a href=\"https://salonapp-springboot.herokuapp.com/verify?verificationCode="
						+ verificationCode + "\">Verify</a><br/><br/>Thank you,<br />Salon Appointment Manager Staff");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			System.out.println(response.statusCode);
			System.out.println(response.body);
			System.out.println(response.headers);
		} catch (IOException ex) {
			throw ex;
		}
	}
}