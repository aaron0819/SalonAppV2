package com.hampson.salonapp.iface;

import java.util.List;

import javax.sql.DataSource;

import com.hampson.salonapp.model.Appointment;
import com.hampson.salonapp.model.PendingAppointment;

public interface AppointmentDAO {

	public void setDataSource(DataSource ds);

	public void createAppointment(String service, String date, String startTime, String endTime, int stylistId, long customerId);

	public Appointment getAppointment(int id);

	public List<Appointment> getAppointmentsByStylistId(int stylistId);
	
	public List<Appointment> getAppointmentsByCustomerId(int customerId);

	public void deleteAppointment(int id);

	public void updateAppointment(String service, String date, String startTime, String endTime, int stylistId);

	public void requestAppointment(String appointmentType, int customerId, String appointmentDate, String alternateAppointmentDate,
			String appointmentStartTime, String alternateAppointmentTime, String preferredStylist);
	
	public List<PendingAppointment> getPendingAppointmentsByStylistId(int stylistId);
	
	public List<PendingAppointment> getPendingAppointmentsWithNoStylistPreference();
}
