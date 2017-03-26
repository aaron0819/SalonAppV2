package com.hampson.salonapp.model;

public class Service {
	private int id;
	private String description;
	private double price;
	private String duration;

	public Service() {
		super();
	}
	
	public Service(int id, String description, double price, int duration) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.setDuration(duration);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		if(duration < 60) {
			this.duration = duration + " minutes";
		} else if(duration == 60){
			this.duration = "1 hour";
		} else {
			this.duration = (duration / 60) + " hour, " + (duration % 60) + " minutes";
		}
	}

}
