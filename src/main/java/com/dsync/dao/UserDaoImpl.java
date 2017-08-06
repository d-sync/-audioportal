package com.dsync.dao;

import com.dsync.configs.HibernateConfig;
import com.dsync.model.AudioContent;
import com.dsync.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoImpl implements UserDao{

	private final SessionFactory sessionFactory;

	public UserDaoImpl() {
		sessionFactory = HibernateConfig.getSessionFactory();
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
	public boolean isUserExists(String msisdn) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String query = String.format("FROM User WHERE user_msisdn = '%s'", msisdn);
		List<User> users =  session.createQuery(query).list();
		transaction.commit();
		session.close();
		if (!users.isEmpty()) return true;
		return false;
	}

	@Override
	public void updateUser(String msisdn, AudioContent audioContent) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		User user = getUserByMSISDN(msisdn);
		user.addAudioContent(audioContent);
		session.update(user);
		transaction.commit();
		session.close();
	}

	@Override
	public User getUserByMSISDN(String msisdn) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String query = String.format("FROM User WHERE user_msisdn = '%s'", msisdn);
		User user = (User) session.createQuery(query).getSingleResult();
		transaction.commit();
		session.close();
		return user;
	}

	@Override
	public void update(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
	}
}
