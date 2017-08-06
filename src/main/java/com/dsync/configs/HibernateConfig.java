package com.dsync.configs;

import com.dsync.model.AudioContent;
import com.dsync.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfig {

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(AudioContent.class);
//		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/base");
//		configuration.setProperty("hibernate.connection.username", "root");
//		configuration.setProperty("hibernate.connection.password", "sqlpassword777");
		configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/testbase");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "root");

		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
