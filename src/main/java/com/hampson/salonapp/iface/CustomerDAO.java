package com.hampson.salonapp.iface;

import com.hampson.salonapp.model.Customer;

public interface CustomerDAO {
	public long createCustomer(String firstName, String lastName, String phoneNumber);

	public Customer getCustomer(String firstName, String lastName, String phoneNumber);
}
