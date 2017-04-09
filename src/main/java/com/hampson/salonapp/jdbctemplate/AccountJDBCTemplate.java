package com.hampson.salonapp.jdbctemplate;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.hampson.salonapp.email.EmailSender;
import com.hampson.salonapp.iface.AccountDAO;

public class AccountJDBCTemplate implements AccountDAO {

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate accountJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.accountJdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String createAccount(String emailAddress, String password, String firstName, String lastName,
			String phoneNumber, String salonCode) {
		String verificationCode = "";
		String returnMessage = "There was an error creating your account. Please try again.";
		String sql = "";
		int customerCode = 0;
		int stylistId = 0;
		int numericSalonCode = 0;
		SecureRandom random = null;

		try {
			numericSalonCode = Integer.parseInt(salonCode);
		} catch (NumberFormatException nfe) {
			numericSalonCode = 0;
		}

		password = BCrypt.hashpw(password, BCrypt.gensalt());
		random = new SecureRandom();
		verificationCode = new BigInteger(130, random).toString(32);

		if (0 == numericSalonCode) {
			sql = "INSERT INTO Customers(customer_first_name, customer_last_name, customer_phone_number) VALUES(?, ?, ?) ";
			getAccountJdbcTemplate().update(sql, new Object[] { firstName, lastName, phoneNumber });
			sql = "SELECT id FROM Customers ORDER BY id DESC LIMIT 1";
			customerCode = getAccountJdbcTemplate().queryForObject(sql, Integer.class);
		} else {
			sql = "INSERT INTO Stylists(stylist_first_name, stylist_last_name, salon_code) VALUES(?, ?, ?)";
			getAccountJdbcTemplate().update(sql, new Object[] { firstName, lastName, numericSalonCode });
			sql = "SELECT id FROM Stylists ORDER BY id DESC LIMIT 1";
			stylistId = getAccountJdbcTemplate().queryForObject(sql, Integer.class);
		}

		sql = "insert into Accounts (email_address, password, customer_id, stylist_id, verification_code) values (?, ?, ?, ?, ?)";
		if (1 == getAccountJdbcTemplate().update(sql, emailAddress, password, customerCode, stylistId,
				verificationCode)) {
			returnMessage = "Account Created Successfully. Please verify your account using the verification email we have sent to you at the email address you signed up with.";
			try {
				EmailSender.sendEmail(emailAddress, verificationCode);
			} catch (IOException e) {
				System.out.println("There was an error sending user verification email for email: " + emailAddress);
			}
		}

		return returnMessage;
	}

	@Override
	public String getPasswordHash(String emailAddress) {
		String sql = "SELECT password FROM Accounts WHERE email_address = ?";

		String password = "";

		try {
			password = getAccountJdbcTemplate().queryForObject(sql, new Object[] { emailAddress }, String.class);
		} catch (EmptyResultDataAccessException e) {
			password = null;
		}

		return password;

	}

	public JdbcTemplate getAccountJdbcTemplate() {
		return accountJdbcTemplate;
	}

	@Override
	public int getAccountType(String emailAddress, HttpServletRequest request) {
		String sql = "SELECT stylist_id, customer_id FROM Accounts WHERE email_address = ?";

		return accountJdbcTemplate.query(sql, new Object[] { emailAddress }, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {
				int accountType = 0;

				if (0 != rs.getInt("customer_id")) {
					accountType = 1;
					request.getSession().setAttribute("customerId", rs.getInt("customer_id"));
				} else if (0 != rs.getInt("stylist_id")) {
					accountType = 2;
					request.getSession().setAttribute("stylistId", rs.getInt("stylist_id"));
				}

				return accountType;
			}
		}).get(0).intValue();
	}

	@Override
	public String[] getAccountByEmailAddress(String emailAddress) {
		String sql = "SELECT customer_id, stylist_id, verification_code FROM Accounts WHERE email_address = ?";

		String[] account;
		
		try {

			account = getAccountJdbcTemplate().query(sql, new Object[] { emailAddress },
					new ResultSetExtractor<String[]>() {

						@Override
						public String[] extractData(ResultSet rs) throws SQLException, DataAccessException {

							String[] acc = new String[3];

							if (rs.next()) {
								acc[0] = rs.getString("customer_id");
								acc[1] = rs.getString("stylist_id");
								acc[2] = rs.getString("verification_code");
							}
							return acc;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			account = null;
		}
		return account;
	}

	@Override
	public void verifyAccount(String emailAddress) {
		
	}

}
