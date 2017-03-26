package com.hampson.salonapp.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hampson.salonapp.iface.ServiceDAO;
import com.hampson.salonapp.jdbctemplate.ServiceJDBCTemplate;
import com.hampson.salonapp.model.Service;

public class ServiceService {

	private ServiceDAO serviceDao;
	
	public ServiceService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		setServiceDao((ServiceJDBCTemplate) context.getBean("serviceJDBCTemplate"));
		((ConfigurableApplicationContext) context).close();
	}

	public List<Service> getAllServices() {
		return getServiceDao().getAllServices();
	}
	
	public ServiceDAO getServiceDao() {
		return serviceDao;
	}

	public void setServiceDao(ServiceDAO serviceDao) {
		this.serviceDao = serviceDao;
	}
	
}
