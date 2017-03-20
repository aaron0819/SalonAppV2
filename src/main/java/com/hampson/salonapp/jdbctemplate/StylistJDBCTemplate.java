package com.hampson.salonapp.jdbctemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hampson.salonapp.iface.StylistDAO;
import com.hampson.salonapp.model.Stylist;

public class StylistJDBCTemplate implements StylistDAO {

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Stylist> getAllStylists() {
		String sql = "SELECT id, stylist_first_name, stylist_last_name FROM Stylists";

		List<Stylist> stylists = new ArrayList<Stylist>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		for (Map<String, Object> row : rows) {
			Stylist stylist = new Stylist((String) row.get("stylist_first_name"),
					(String) row.get("stylist_last_name"));
			stylist.setId((int)row.get("id"));
			stylists.add(stylist);
		}

		return stylists;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
