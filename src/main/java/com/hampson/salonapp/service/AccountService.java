package com.hampson.salonapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.hampson.salonapp.iface.AccountDAO;
import com.hampson.salonapp.jdbctemplate.AccountJDBCTemplate;

public class AccountService {

	private AccountDAO accountJDBCTemplate;
	
	public AccountService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setAccountJDBCTemplate((AccountJDBCTemplate) context.getBean("accountJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}

	/*
	 * Return Codes:
	 * 0 - Invalid Credentials
	 * 1 - Successful customer login
	 * 2 - Successful stylist login
	 * 3 - Email not verified
	 */
	public int login(String emailAddress, String password, HttpServletRequest request) {
		int returnCode = 0;
		
		if(null == emailAddress || null == password) {
			return returnCode;
		}
		
		String hashedPassword = getAccountJDBCTemplate().getPasswordHash(emailAddress);
		String verificationCode = getAccountJDBCTemplate().getVerificationCode(emailAddress);
		
		if(verificationCode != null) {
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

	public String createAccount(String emailAddress, String password, String firstName, String lastName, String phoneNumber, String salonCode) {
		return getAccountJDBCTemplate().createAccount(emailAddress, password, firstName, lastName, phoneNumber, salonCode);
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
		getAccountJDBCTemplate().getCustomerEmailAddress(customerId);
		return null;
	}

}
