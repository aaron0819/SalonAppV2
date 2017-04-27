package com.hampson.salonapp.iface;

import javax.servlet.http.HttpServletRequest;

public interface AccountDAO {
	
	public String createAccount(String emailAddress, String password, String firstName, String lastName, String phoneNumber, String stylistCode);
	
	public String getPasswordHash(String username);
	
	public int getAccountType(String emailAddress, HttpServletRequest request);
	
	public String[] getAccountByEmailAddress(String emailAddress);
	
	public void verifyAccount(String emailAddress);

	public String getVerificationCode(String emailAddress);

	public String getCustomerEmailAddress(int customerId);

	public int updatePassword(String emailAddress, String tempPassword);
}
