package com.hampson.salonapp.util;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;
import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;

public class PasswordUtil {

	public static String generateTemporaryPassword() {
		return randomAlphanumeric(8).toUpperCase();
	}

	public static String encryptPassword(String password) {
		return hashpw(password, gensalt());
	}
}
