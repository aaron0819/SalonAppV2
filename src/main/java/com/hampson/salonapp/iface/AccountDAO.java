package com.hampson.salonapp.iface;

import javax.servlet.http.HttpServletRequest;

public interface AccountDAO {
	
	public String createAccount(String emailAddress, String password, String firstName, String lastName, String phoneNumber, String stylistCode);
	
	public String getPasswordHash(String username);
	
	public int getAccountType(String emailAddress, HttpServletRequest request);
}
