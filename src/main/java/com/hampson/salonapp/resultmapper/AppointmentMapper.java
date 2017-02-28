package com.hampson.salonapp.resultmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hampson.salonapp.model.Appointment;
import com.hampson.salonapp.model.Customer;
import com.hampson.salonapp.model.Stylist;

public class AppointmentMapper implements RowMapper<Appointment> {
	public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Appointment appointment = new Appointment();
		
		appointment.setAppointmentDesc(rs.getString("service"));
		appointment.setDate(rs.getString("date"));
		appointment.setStartTime(rs.getString("time_from"));
		appointment.setEndTime(rs.getString("time_to"));
		appointment.setStylist(new Stylist(rs.getString("stylist_first_name"), rs.getString("stylist_last_name")));
		appointment.setCustomer(new Customer(rs.getString("customer_first_name"), rs.getString("customer_last_name"), rs.getString("customer_phone_number")));
		
		return appointment;
	}
}		
