package com.dsync.services;

import com.dsync.dao.AudioContentDao;
import com.dsync.dao.AudioContentDaoImpl;
import com.dsync.dao.UserDao;
import com.dsync.dao.UserDaoImpl;
import com.dsync.model.AudioContent;
import com.dsync.model.User;

import java.util.Set;

public class AudioContentServiceImpl implements AudioContentService {

	private AudioContentDao audioContentDao = new AudioContentDaoImpl();

	@Override
	public Set<AudioContent> getUserAudioContent(String msisdn) {
		return audioContentDao.getUserAudioContent(msisdn);
	}

	@Override
	public Set<AudioContent> getPopularAudioContent() {
		return audioContentDao.getPopularAudioContent();
	}

	@Override
	public Set<AudioContent> getNewAudioContent() {
		return audioContentDao.getNewAudioContent();
	}

	@Override
	public Set<AudioContent> getHitsAudioContent() {
		return audioContentDao.getHitAudioContent();
	}



}
