package com.hampson.salonapp.util;

import com.hampson.salonapp.model.AppointmentRequest;

public class AppointmentUtils {

	public static AppointmentRequest buildAppointmentObject(String appointmentType, int customerId,
			String appointmentDate, String alternateAppointmentDate, String appointmentStartTime,
			String alternateAppointmentTime, String preferredStylist) {
		AppointmentRequest apptRequest = new AppointmentRequest();

			apptRequest.setAppointmentType(appointmentType);
			apptRequest.setCustomerId(customerId);
			apptRequest.setAppointmentDate(appointmentDate);
			apptRequest.setAlternateAppointmentDate(alternateAppointmentDate);
			apptRequest.setAppointmentStartTime(appointmentStartTime);
			apptRequest.setAlternateAppointmentTime(alternateAppointmentTime);
			apptRequest.setPreferredStylist(preferredStylist);
		
		return apptRequest;
	}

}
