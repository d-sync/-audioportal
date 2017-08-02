package com.dsync.dao;

import com.dsync.model.AudioContent;
import com.dsync.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class UserDaoImpl implements UserDao{

	private static UserDaoImpl ourInstance = new UserDaoImpl();
	private final SessionFactory sessionFactory;

	public static UserDaoImpl getInstance() {
		return ourInstance;
	}

	private UserDaoImpl() {
		Configuration configuration = getMySqlConfiguration();
		sessionFactory = createSessionFactory(configuration);
	}

	private Configuration getMySqlConfiguration() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(AudioContent.class);
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/base");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "sqlpassword777");
		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		return configuration;
	}

	private static SessionFactory createSessionFactory(Configuration configuration) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	@Override
	public void save(User entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(entity);
		transaction.commit();
		session.close();
	}

	@Override
	public User getById(Long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, id);
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public boolean isUserExists(String name) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String query = String.format("FROM User WHERE user_name = '%s'", name);
		List<User> users =  session.createQuery(query).list();
		transaction.commit();
		session.close();
		if (!users.isEmpty()) return true;
		return false;
	}
}
