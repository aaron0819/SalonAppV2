package com.hampson.salonapp.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hampson.salonapp.iface.CustomerDAO;
import com.hampson.salonapp.model.Customer;

public class CustomerJDBCTemplate implements CustomerDAO {

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate customerJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.customerJdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long createCustomer(String firstName, String lastName, String phoneNumber) {
		String sql = "INSERT INTO Customers(customer_first_name, customer_last_name, customer_phone_number) VALUES(?, ?, ?) ";
		getCustomerJdbcTemplate().update(sql, new Object[] { firstName, lastName, phoneNumber });
		
		sql = "SELECT id, customer_first_name, customer_last_name, customer_phone_number FROM Customers WHERE customer_first_name = ? and customer_last_name = ? and customer_phone_number = ?";

		return customerJdbcTemplate.query(sql, new Object[] { firstName, lastName, phoneNumber }, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {
				return rs.getInt("id");
			}
		}).get(0);
	}

	@Override
	public Customer getCustomer(String firstName, String lastName, String phoneNumber) {
		String sql = "SELECT id, customer_first_name, customer_last_name, customer_phone_number FROM Customers WHERE customer_first_name = ? and customer_last_name = ? and customer_phone_number = ?";

		List<Customer> c = customerJdbcTemplate.query(sql, new Object[] { firstName, lastName, phoneNumber }, new RowMapper<Customer>() {
			@Override
			public Customer mapRow(ResultSet rs, int rownumber) throws SQLException {
				Customer customer = new Customer();
				
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("customer_first_name"));
				customer.setLastName(rs.getString("customer_last_name"));
				customer.setPhoneNumber(rs.getString("customer_phone_number"));
				
				return customer;
			}
		});
		
		return (0 == c.size() ? null : c.get(0));
	}

	public JdbcTemplate getCustomerJdbcTemplate() {
		return customerJdbcTemplate;
	}

}
