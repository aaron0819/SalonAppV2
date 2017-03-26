package com.hampson.salonapp.jdbctemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hampson.salonapp.iface.ServiceDAO;
import com.hampson.salonapp.model.Service;

public class ServiceJDBCTemplate implements ServiceDAO {

	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate serviceJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.serviceJdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Service> getAllServices() {
		String sql = "SELECT id, description, price, duration FROM services";
		
		List<Service> services = new ArrayList<Service>();
		
		List<Map<String, Object>> rows = getServiceJdbcTemplate().queryForList(sql);

		for (Map<String, Object> row : rows) {
			Service service = new Service();

			service.setId((int) row.get("id"));
			service.setDescription((String) row.get("description"));
			service.setPrice(((BigDecimal) row.get("price")).doubleValue());
			service.setDuration((int) row.get("duration"));

			services.add(service);
		}
		
		return services;
	}

	public JdbcTemplate getServiceJdbcTemplate() {
		return serviceJdbcTemplate;
	}
}
