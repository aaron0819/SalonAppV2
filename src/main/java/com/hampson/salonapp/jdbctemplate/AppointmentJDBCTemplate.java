package com.hampson.salonapp.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.hampson.salonapp.iface.AppointmentDAO;
import com.hampson.salonapp.model.Appointment;
import com.hampson.salonapp.model.AppointmentRequest;
import com.hampson.salonapp.model.Customer;
import com.hampson.salonapp.model.PendingAppointment;
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
		String sql = "INSERT into Appointments (service, date, time_from, time_to, stylist_id, customer_id) values (?, ?, ?, ?, ?, ?)";

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
		String sql = "SELECT Appointments.service, Appointments.date, Appointments.time_from, Appointments.time_to, Appointments.stylist_id, Appointments.customer_id, Stylists.stylist_first_name, Stylists.stylist_last_name FROM Appointments INNER JOIN Customers ON Appointments.customer_id = Customers.id INNER JOIN Stylists on Appointments.stylist_id = Stylists.id WHERE Customers.id = ?";

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

	@Override
	public int requestAppointment(AppointmentRequest apptRequest) {

		String sql = "INSERT into pendingappointments (service, requested_date, alternate_date, requested_time, alternate_time, stylist_id, customer_id) values (?, ?, ?, ?, ?, ?, ?)";

		return getJdbcTemplate().update(sql, apptRequest.getAppointmentType(), apptRequest.getAppointmentDate(),
				apptRequest.getAlternateAppointmentDate(), apptRequest.getAppointmentStartTime(),
				apptRequest.getAlternateAppointmentTime(), Integer.parseInt(apptRequest.getPreferredStylist()),
				apptRequest.getCustomerId());
	}

	@Override
	public List<PendingAppointment> getPendingAppointmentsByStylistId(int stylistId) {
		String sql = "SELECT PendingAppointments.id, PendingAppointments.service, PendingAppointments.requested_date, PendingAppointments.alternate_date, PendingAppointments.requested_time, PendingAppointments.alternate_time, PendingAppointments.stylist_id, PendingAppointments.customer_id, Customers.customer_first_name, Customers.customer_last_name, Customers.customer_phone_number, Stylists.stylist_first_name, Stylist_last_name FROM pendingappointments INNER JOIN Customers ON Pendingappointments.customer_id = Customers.id INNER JOIN Stylists ON Pendingappointments.stylist_id = Stylists.id WHERE Pendingappointments.stylist_id = ?";

		List<PendingAppointment> appointments = new ArrayList<PendingAppointment>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { stylistId });

		for (Map<String, Object> row : rows) {
			PendingAppointment pending = new PendingAppointment();

			pending.setId((int) row.get("id"));
			pending.setService((String) row.get("service"));
			pending.setRequestedDate((String) row.get("requested_date"));
			pending.setRequestedTime((String) row.get("requested_time"));
			pending.setAlternateDate((String) row.get("alternate_date"));
			pending.setAlternateTime((String) row.get("alternate_time"));
			pending.setCustomer(new Customer((String) row.get("customer_first_name"),
					(String) row.get("customer_last_name"), (String) row.get("customer_phone_number")));
			pending.setStylist(
					new Stylist((String) row.get("stylist_first_name"), (String) row.get("stylist_last_name")));

			appointments.add(pending);
		}

		return appointments;
	}

	@Override
	public List<PendingAppointment> getPendingAppointmentsWithNoStylistPreference() {
		String sql = "SELECT PendingAppointments.id, PendingAppointments.service, PendingAppointments.requested_date, PendingAppointments.alternate_date, PendingAppointments.requested_time, PendingAppointments.alternate_time, PendingAppointments.stylist_id, PendingAppointments.customer_id, Customers.customer_first_name, Customers.customer_last_name, Customers.customer_phone_number FROM pendingappointments INNER JOIN Customers ON Pendingappointments.customer_id = Customers.id WHERE Pendingappointments.stylist_id = 0";

		List<PendingAppointment> appointments = new ArrayList<PendingAppointment>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		for (Map<String, Object> row : rows) {
			PendingAppointment pending = new PendingAppointment();

			pending.setId((int) row.get("id"));
			pending.setService((String) row.get("service"));
			pending.setRequestedDate((String) row.get("requested_date"));
			pending.setRequestedTime((String) row.get("requested_time"));
			pending.setAlternateDate((String) row.get("alternate_date"));
			pending.setAlternateTime((String) row.get("alternate_time"));
			pending.setCustomer(new Customer((String) row.get("customer_first_name"),
					(String) row.get("customer_last_name"), (String) row.get("customer_phone_number")));
			pending.setStylist(new Stylist("No", "Preference"));

			appointments.add(pending);
		}

		return appointments;
	}

	/**
	 * This method will first retrieve the pending appointment that a stylist
	 * now confirmed from the pending appointments table, then it will move it
	 * to the confirmed appointments table and remove it from the pending
	 * appointments table
	 * 
	 * @param pendingAppointmentId
	 * @throws Exception
	 */
	@Override
	public PendingAppointment confirmAppointment(int pendingAppointmentId) throws Exception {
		String sql = "SELECT PendingAppointments.id, PendingAppointments.service, PendingAppointments.requested_date, PendingAppointments.alternate_date, PendingAppointments.requested_time, PendingAppointments.alternate_time, PendingAppointments.stylist_id, PendingAppointments.customer_id, Accounts.email_address FROM pendingappointments INNER JOIN Accounts ON PendingAppointments.customer_id = Accounts.customer_id WHERE id = ? LIMIT 1";

		PendingAppointment pending = getJdbcTemplate().query(sql, new Object[] { pendingAppointmentId },
				new ResultSetExtractor<PendingAppointment>() {

					@Override
					public PendingAppointment extractData(ResultSet rs) throws SQLException, DataAccessException {

						PendingAppointment appt = new PendingAppointment();
						Customer c = new Customer();
						Stylist s = new Stylist();

						if (rs.next()) {

							c.setId(rs.getLong("customer_id"));
							c.setEmailAddress(rs.getString("email_address"));
							s.setId(rs.getInt("stylist_id"));

							appt.setId(rs.getInt("id"));
							appt.setService(rs.getString("service"));
							appt.setRequestedDate(rs.getString("requested_date"));
							appt.setRequestedTime(rs.getString("requested_time"));
							appt.setCustomer(c);
							appt.setStylist(s);
						}
						return appt;
					}
				});

		createAppointment(pending.getService(), pending.getRequestedDate(), pending.getRequestedTime(), "",
				pending.getStylist().getId(), pending.getCustomer().getId());

		sql = "DELETE FROM pendingappointments WHERE id = ?";

		int rows = getJdbcTemplate().update(sql, new Object[] { pending.getId() });

		if (rows != 1) {
			throw new Exception("There was an error confirming the appointment");
		}

		return pending;
	}

	@Override
	public List<PendingAppointment> getPendingAppointmentsByCustomerId(int customerId) {
		String sql = "SELECT id, service, requested_date, alternate_date, requested_time, alternate_time, stylist_id, customer_id FROM pendingappointments WHERE customer_id = ?";

		List<PendingAppointment> appointments = new ArrayList<PendingAppointment>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, new Object[] { customerId });

		for (Map<String, Object> row : rows) {
			PendingAppointment pending = new PendingAppointment();

			pending.setId((int) row.get("id"));
			pending.setService((String) row.get("service"));
			pending.setRequestedDate((String) row.get("requested_date"));
			pending.setRequestedTime((String) row.get("requested_time"));
			pending.setAlternateDate((String) row.get("alternate_date"));
			pending.setAlternateTime((String) row.get("alternate_time"));
			pending.setCustomer(new Customer((String) row.get("customer_first_name"),
					(String) row.get("customer_last_name"), (String) row.get("customer_phone_number")));
			pending.setStylist(new Stylist("No", "Preferred Stylist Indicated"));

			appointments.add(pending);
		}

		return appointments;
	}

}
