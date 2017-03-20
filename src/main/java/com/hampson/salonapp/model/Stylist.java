package com.hampson.salonapp.model;

import java.io.Serializable;

public class Stylist implements Serializable {
	private static final long serialVersionUID = 2411137916291785766L;

	private int id;
	private String firstName;
	private String lastName;

	public Stylist() {
		super();
	}

	public Stylist(String firstName, String lastName) {
		super();
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}
