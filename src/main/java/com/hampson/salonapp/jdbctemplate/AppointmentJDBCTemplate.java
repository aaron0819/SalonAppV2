package com.hampson.salonapp.jdbctemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hampson.salonapp.iface.AppointmentDAO;
import com.hampson.salonapp.model.Appointment;
import com.hampson.salonapp.model.Customer;
import com.hampson.salonapp.model.Stylist;
import com.hampson.salonapp.resultmapper.AppointmentMapper;

public class AppointmentJDBCTemplate implements AppointmentDAO {
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createAppointment(String service, String date, String startTime, String endTime, int stylistId,
			long customerId) {
		String sql = "insert into Appointments (service, date, time_from, time_to, stylist_id, customer_id) values (?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(sql, service, date, startTime, endTime, stylistId, customerId);
	}

	@Override
	public Appointment getAppointment(int id) {
		String sql = "SELECT Appointments.service, Appointments.date, Appointments.time_from, Appointments.time_to, Appointments.stylist_id, Appointments.customer_id, Customers.customer_first_name, Customers.customer_last_name, Customers.customer_phone_number, Stylists.stylist_first_name, Stylists.stylist_last_name FROM Appointments INNER JOIN Customers ON Appointments.customer_id = Customers.id INNER JOIN Stylists on Appointments.stylist_id = Stylists.id WHERE Appointments.id = ?";

		Appointment appointment = (Appointment) getJdbcTemplate().queryForObject(sql, new Object[] { id },
				new AppointmentMapper());

		return appointment;
	}

	@Override
	public List<Appointment> getAppointmentsByStylistId(int stylistId) {
		String sql = "SELECT Appointments.service, Appointments.date, Appointments.time_from, Appointments.time_to, Appointments.stylist_id, Appointments.customer_id, Customers.customer_first_name, Customers.customer_last_name, Customers.customer_phone_number, Stylists.stylist_first_name, Stylists.stylist_last_name FROM Appointments INNER JOIN Customers ON Appointments.customer_id = Customers.id INNER JOIN Stylists on Appointments.stylist_id = Stylists.id WHERE Stylists.id = ?";

		List<Appointment> appointments = new ArrayList<Appointment>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { stylistId });

		for (Map<String, Object> row : rows) {
			Appointment appointment = new Appointment();

			appointment.setAppointmentDesc((String) row.get("service"));
			appointment.setDate((String) row.get("date"));
			appointment.setStartTime((String) row.get("time_from"));
			appointment.setEndTime((String) row.get("time_to"));
			appointment.setStylist(
					new Stylist((String) row.get("stylist_first_name"), (String) row.get("stylist_last_name")));
			appointment.setCustomer(new Customer((String) row.get("customer_first_name"),
					(String) row.get("customer_last_name"), (String) row.get("customer_phone_number")));

			appointments.add(appointment);
		}

		return appointments;
	}

	@Override
	public List<Appointment> getAppointmentsByCustomerId(int customerId) {
		String sql = "SELECT Appointments.service, Appointments.date, Appointments.time_from, Appointments.time_to, Appointments.stylist_id, Appointments.customer_id, Customers.customer_first_name, Customers.customer_last_name, Customers.customer_phone_number, Stylists.stylist_first_name, Stylists.stylist_last_name FROM Appointments INNER JOIN Customers ON Appointments.customer_id = Customers.id INNER JOIN Stylists on Appointments.stylist_id = Stylists.id WHERE Customers.id = ?";

		List<Appointment> appointments = new ArrayList<Appointment>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { customerId });

		for (Map<String, Object> row : rows) {
			Appointment appointment = new Appointment();

			appointment.setAppointmentDesc((String) row.get("service"));
			appointment.setDate((String) row.get("date"));
			appointment.setStartTime((String) row.get("time_from"));
			appointment.setEndTime((String) row.get("time_to"));
			appointment.setStylist(
					new Stylist((String) row.get("stylist_first_name"), (String) row.get("stylist_last_name")));
			appointment.setCustomer(new Customer((String) row.get("customer_first_name"),
					(String) row.get("customer_last_name"), (String) row.get("customer_phone_number")));

			appointments.add(appointment);
		}

		return appointments;
	}

	@Override
	public void deleteAppointment(int id) {
		String sql = "DELETE FROM Appointments WHERE id = ?";

		Object[] params = { id };

		getJdbcTemplate().update(sql, params);
	}

	@Override
	public void updateAppointment(String service, String date, String startTime, String endTime, int stylistId) {
		String sql = "UPDATE Appointments SET service = ?, date = ?, time_from = ?, time_to = ?, stylist_id = ?";

		Object[] params = { service, date, startTime, endTime, stylistId };

		getJdbcTemplate().update(sql, params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
