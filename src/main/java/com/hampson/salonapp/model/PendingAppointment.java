package com.hampson.salonapp.model;

public class PendingAppointment {
	private int id;
	private String service;
	private String requestedDate;
	private String alternateDate;
	private String requestedTime;
	private String alternateTime;
	private Customer customer;
	private String stylist;

	public PendingAppointment() {
		super();
	}
	
	public PendingAppointment(int id, String service, String requestedDate, String alternateDate, String requestedTime,
			String alternateTime, Customer customer, String stylist) {
		super();
		this.id = id;
		this.service = service;
		this.requestedDate = requestedDate;
		this.alternateDate = alternateDate;
		this.requestedTime = requestedTime;
		this.alternateTime = alternateTime;
		this.customer = customer;
		this.stylist = stylist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getAlternateDate() {
		return alternateDate;
	}

	public void setAlternateDate(String alternateDate) {
		this.alternateDate = alternateDate;
	}

	public String getRequestedTime() {
		return requestedTime;
	}

	public void setRequestedTime(String requestedTime) {
		this.requestedTime = requestedTime;
	}

	public String getAlternateTime() {
		return alternateTime;
	}

	public void setAlternateTime(String alternateTime) {
		this.alternateTime = alternateTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getStylist() {
		return stylist;
	}

	public void setStylist(String stylist) {
		this.stylist = stylist;
	}

}
