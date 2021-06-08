package com.sapient.programs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sapient.cfg.AppConfig1;
import com.sapient.dao.ProductDao;

public class P01_GetProductCount {

	public static void main(String[] args) {
//        our dependency
		ProductDao dao;
		
//        define a variable representing the spring container
		AnnotationConfigApplicationContext ctx;
		
//		object of spring container
		ctx = new AnnotationConfigApplicationContext(AppConfig1.class);
//         spring container will read functions annotated with @Bean inside Appconfig class
	   
		// after the above instantiation of Appconfig, it has created objects returned from Bean methods of AppConfig
		// after we call the next getBean() it just returns the objs already present in container
		
		dao = ctx.getBean("jdbcDao", ProductDao.class);
		System.out.println("dao is an instanceof "+ dao.getClass().getName());
		System.out.println("There are "+dao.count() +" products.");
		ctx.close();
		
	}

}
