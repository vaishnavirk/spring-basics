package com.sapient.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import com.sapient.dao.*;

@PropertySource("classpath:jdbc.properties")
@Configuration
public class AppConfig1 {

	@Value("${driver}")
	private String driverClassName;
	@Value("${user}")
	private String user;
	@Value("${url}")
	private String url;
	@Value("${pwd}")
	private String pwd;
	
	public AppConfig1() {
		System.out.println("AppConfig1 instantiated");
	}
	@Lazy
	@Bean
	public DummyProductDao dummyDao() {
		System.out.println("AppConfig1.dummyDao called");
		return new DummyProductDao();
		
	}
	
	@Lazy
	@Scope("singleton")
	@Bean
	public JdbcProductDao jdbcDao() {
		System.out.println("AppConfig1.JdbcDao called");
        JdbcProductDao dao = new JdbcProductDao();
        dao.setDriverClassName(driverClassName);
        dao.setUrl(url);
        dao.setUser(user);
        dao.setPwd(pwd);
        
		return dao;
	}
}
