package com.hampson.salonapp.model;

public class AppointmentRequest {
	private String appointmentType;
	private int customerId;
	private String appointmentDate;
	private String alternateAppointmentDate;
	private String appointmentStartTime;
	private String alternateAppointmentTime;
	private String preferredStylist;
	
	public AppointmentRequest() {
		super();
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAlternateAppointmentDate() {
		return alternateAppointmentDate;
	}

	public void setAlternateAppointmentDate(String alternateAppointmentDate) {
		this.alternateAppointmentDate = alternateAppointmentDate;
	}

	public String getAppointmentStartTime() {
		return appointmentStartTime;
	}

	public void setAppointmentStartTime(String appointmentStartTime) {
		this.appointmentStartTime = appointmentStartTime;
	}

	public String getAlternateAppointmentTime() {
		return alternateAppointmentTime;
	}

	public void setAlternateAppointmentTime(String alternateAppointmentTime) {
		this.alternateAppointmentTime = alternateAppointmentTime;
	}

	public String getPreferredStylist() {
		return preferredStylist;
	}

	public void setPreferredStylist(String preferredStylist) {
		this.preferredStylist = preferredStylist;
	}
}
