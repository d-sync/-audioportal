package com.dsync.dao;

import com.dsync.configs.HibernateConfig;
import com.dsync.model.AudioContent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AudioContentDaoImpl implements AudioContentDao {

	private final SessionFactory sessionFactory;

	public AudioContentDaoImpl() {
		sessionFactory = HibernateConfig.getSessionFactory();
	}

	@Override
	public Set<AudioContent> getUserAudioContent(String msisdn) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String query = "SELECT ac FROM User u JOIN u.audioContents ac WHERE u.msisdn = :msisdn";
		List<AudioContent> audioContents = session.createQuery(query).setParameter("msisdn", msisdn).list();
		transaction.commit();
		session.close();
		return new HashSet<>(audioContents);
	}

	@Override
	public Set<AudioContent> getPopularAudioContent() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String nativeQuery = "SELECT * FROM audio_content ac WHERE ac.isPopular = 1";
		List<AudioContent> pops = session.createNativeQuery(nativeQuery, AudioContent.class).list();
		transaction.commit();
		session.close();
		return new HashSet<>(pops);
	}

	@Override
	public Set<AudioContent> getNewAudioContent() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String nativeQuery = "SELECT * FROM audio_content ac WHERE ac.isNew = 1";
		List<AudioContent> newAudio = session.createNativeQuery(nativeQuery, AudioContent.class).list();
		transaction.commit();
		session.close();
		return new HashSet<>(newAudio);
	}

	@Override
	public Set<AudioContent> getHitAudioContent() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String nativeQuery = "SELECT * FROM audio_content ac WHERE ac.isHit = 1";
		List<AudioContent> hits = session.createNativeQuery(nativeQuery, AudioContent.class).list();
		transaction.commit();
		session.close();
		return new HashSet<>(hits);
	}

	@Override
	public void save(AudioContent entity) {

	}

}
