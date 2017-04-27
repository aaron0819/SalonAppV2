package com.hampson.salonapp.util;

import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

	public static String generateTemporaryPassword() {
		SecureRandom random = new SecureRandom();
		
		String tempPassword = new BigInteger(130, random).toString(8);
				
		return tempPassword;
	}
	
	public static String encryptPassword(String password) {
		return hashpw(password, BCrypt.gensalt());
	}
}
