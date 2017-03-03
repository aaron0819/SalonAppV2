package com.hampson.salonapp.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.iface.CustomerDAO;
import com.hampson.salonapp.jdbctemplate.CustomerJDBCTemplate;
import com.hampson.salonapp.model.Customer;

public class CustomerService {
	private CustomerDAO customerJDBCTemplate;

	public CustomerService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setCustomerJDBCTemplate((CustomerJDBCTemplate) context.getBean("customerJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}

	public Customer getCustomer(String firstName, String lastName, String phoneNumber) {
		return getCustomerJDBCTemplate().getCustomer(firstName, lastName, phoneNumber);
	}

	public boolean checkIfCustomerExists(String firstName, String lastName, String phoneNumber) {
		return (null == getCustomerJDBCTemplate().getCustomer(firstName, lastName, phoneNumber) ? true : false);
	}

	public long createCustomer(String firstName, String lastName, String phoneNumber) {
		return getCustomerJDBCTemplate().createCustomer(firstName, lastName, phoneNumber);
	}

	public CustomerDAO getCustomerJDBCTemplate() {
		return customerJDBCTemplate;
	}

	public void setCustomerJDBCTemplate(CustomerDAO customerJDBCTemplate) {
		this.customerJDBCTemplate = customerJDBCTemplate;
	}
}
