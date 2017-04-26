package com.hampson.salonapp.iface;

import java.util.List;

import com.hampson.salonapp.model.Stylist;

public interface StylistDAO {
	public List<Stylist> getAllStylists();

	public Stylist getStylistById(int id);
}
