package com.hampson.salonapp.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
	public void createAccount(String emailAddress, String password, String firstName, String lastName,
			String phoneNumber, String salonCode) {

		String sql = "";
		int customerCode = 0;
		int stylistId = 0;
		int numericSalonCode = 0;

		try {
			numericSalonCode = Integer.parseInt(salonCode);
		} catch(NumberFormatException nfe) {
			numericSalonCode = 0;
		}
		
		password = BCrypt.hashpw(password, BCrypt.gensalt());
		
		if (0 == numericSalonCode) {
			sql = "INSERT INTO Customers(customer_first_name, customer_last_name, customer_phone_number) VALUES(?, ?, ?) ";
			getAccountJdbcTemplate().update(sql, new Object[] { firstName, lastName, phoneNumber });
			sql = "SELECT id FROM Customers ORDER BY id DESC LIMIT 1";
			customerCode = getAccountJdbcTemplate().queryForObject(sql, Integer.class);
		} else {
			sql = "INSERT INTO Stylists(stylist_first_name, stylist_last_name, salon_code) VALUES(?, ?, ?)";
			getAccountJdbcTemplate().update(sql, new Object[] {firstName, lastName, numericSalonCode});
			sql = "SELECT id FROM Stylists ORDER BY id DESC LIMIT 1";
			stylistId = getAccountJdbcTemplate().queryForObject(sql, Integer.class);
		}

		sql = "insert into Accounts (email_address, password, customer_id, stylist_id) values (?, ?, ?, ?)";
		getAccountJdbcTemplate().update(sql, emailAddress, password, customerCode, stylistId);
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
				} else if (0 != rs.getInt("stylist_id")) {
					accountType = 2;
					request.getSession().setAttribute("stylistId", rs.getInt("stylist_id"));
				}

				return accountType;
			}
		}).get(0).intValue();
	}

}
