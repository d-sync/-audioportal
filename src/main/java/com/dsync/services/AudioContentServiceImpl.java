package com.dsync.services;

import com.dsync.dao.AudioContentDao;
import com.dsync.dao.AudioContentDaoImpl;
import com.dsync.model.AudioContent;

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

	@Override
	public void add(AudioContent audioContent) {
		audioContentDao.save(audioContent);
	}


}
