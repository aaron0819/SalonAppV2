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
	 */
	public int login(String emailAddress, String password, HttpServletRequest request) {
		int returnCode = 0;
		
		if(null == emailAddress || null == password) {
			return returnCode;
		}
		
		String hashedPassword = getAccountJDBCTemplate().getPasswordHash(emailAddress);
		
		if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
			returnCode = getAccountJDBCTemplate().getAccountType(emailAddress, request);
		}
		
		return returnCode;
	}

	public void createAccount(String emailAddress, String password, String firstName, String lastName, String phoneNumber, String salonCode) {
		getAccountJDBCTemplate().createAccount(emailAddress, password, firstName, lastName, phoneNumber, salonCode);
	}
	
	public AccountDAO getAccountJDBCTemplate() {
		return accountJDBCTemplate;
	}

	public void setAccountJDBCTemplate(AccountDAO accountJDBCTemplate) {
		this.accountJDBCTemplate = accountJDBCTemplate;
	}

}
