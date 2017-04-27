package com.hampson.salonapp.service;

import static com.hampson.salonapp.email.EmailSender.sendPasswordResetEmail;
import static com.hampson.salonapp.util.PasswordUtil.encryptPassword;
import static com.hampson.salonapp.util.PasswordUtil.generateTemporaryPassword;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.hampson.salonapp.email.EmailSender;
import com.hampson.salonapp.iface.AccountDAO;
import com.hampson.salonapp.jdbctemplate.AccountJDBCTemplate;
import com.hampson.salonapp.util.PasswordUtil;

public class AccountService {

	private AccountDAO accountJDBCTemplate;

	public AccountService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setAccountJDBCTemplate((AccountJDBCTemplate) context.getBean("accountJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}

	/*
	 * Return Codes: 0 - Invalid Credentials 1 - Successful customer login 2 -
	 * Successful stylist login 3 - Email not verified
	 */
	public int login(String emailAddress, String password, HttpServletRequest request) {
		int returnCode = 0;

		if (null == emailAddress || null == password) {
			return returnCode;
		}

		String hashedPassword = getAccountJDBCTemplate().getPasswordHash(emailAddress);
		String verificationCode = getAccountJDBCTemplate().getVerificationCode(emailAddress);

		if (verificationCode != null) {
			return 3;
		}

		if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
			returnCode = getAccountJDBCTemplate().getAccountType(emailAddress, request);
		}

		return returnCode;
	}

	public String[] retrieveAccountByEmailAddress(String emailAddress) {
		return getAccountJDBCTemplate().getAccountByEmailAddress(emailAddress);
	}

	public String createAccount(String emailAddress, String password, String firstName, String lastName,
			String phoneNumber, String salonCode) {
		return getAccountJDBCTemplate().createAccount(emailAddress, password, firstName, lastName, phoneNumber,
				salonCode);
	}

	public AccountDAO getAccountJDBCTemplate() {
		return accountJDBCTemplate;
	}

	public void setAccountJDBCTemplate(AccountDAO accountJDBCTemplate) {
		this.accountJDBCTemplate = accountJDBCTemplate;
	}

	public void verifyAccount(String emailAddress) {
		getAccountJDBCTemplate().verifyAccount(emailAddress);
	}

	public String getCustomerEmailAddress(int customerId) {
		return getAccountJDBCTemplate().getCustomerEmailAddress(customerId);
	}

	/**
	 * Calls necessary methods to reset a user's password.
	 * 
	 * Return codes: 0 - No account with email on record 1 - JDBC template call
	 * was successful (1 row updated) to update password.
	 * 
	 * @param emailAddress - Email address of the account that the password is being reset for
	 * @return returnMessage - Message that will be displayed to user indicating result of password reset attempt
	 */
	public String resetPassword(String emailAddress) {
		String tempPassword = generateTemporaryPassword();
		String encryptedPassword = encryptPassword(tempPassword);
		int returnCode = getAccountJDBCTemplate().updatePassword(emailAddress, encryptedPassword);
		String returnMessage = "";

		switch (returnCode) {
			case 0:
				returnMessage = "There is no account associated with the email address entered.";
				break;
			case 1:
				try {
					sendPasswordResetEmail(emailAddress, tempPassword);
				} catch (IOException e) {
					returnMessage = "There was an error resetting your password. Please try again";
				}
				break;
			default:
				returnMessage = "There was an error resetting your password. Please try again";
				break;
		}

		return returnMessage;
	}
}
