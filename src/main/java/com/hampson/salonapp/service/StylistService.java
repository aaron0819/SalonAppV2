package com.hampson.salonapp.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.iface.StylistDAO;
import com.hampson.salonapp.jdbctemplate.StylistJDBCTemplate;
import com.hampson.salonapp.model.Stylist;

public class StylistService {

	private StylistDAO stylistJDBCTemplate;
	
	public StylistService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setStylistJDBCTemplate((StylistJDBCTemplate) context.getBean("stylistJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}
	
	public List<Stylist> getAllStylists() {
		return getStylistJDBCTemplate().getAllStylists();
	}

	public void setStylistJDBCTemplate(StylistDAO stylistJDBCTemplate) {
		this.stylistJDBCTemplate = stylistJDBCTemplate;
	}

	public StylistDAO getStylistJDBCTemplate() {
		return stylistJDBCTemplate;
	}

	public Stylist getStylistById(int id) {
		return getStylistJDBCTemplate().getStylistById(id);
	}
}
