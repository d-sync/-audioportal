package com.dsync.services;

import com.dsync.dao.UserDao;
import com.dsync.dao.UserDaoImpl;
import com.dsync.model.AudioContent;
import com.dsync.model.User;


public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();

	@Override
	public boolean isUserExists(String name) {
		return userDao.isUserExists(name);
	}

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	@Override
	public void buyCurrentTrack(String msisdn, AudioContent audioContent) {
		userDao.updateUser(msisdn, audioContent);
	}

	@Override
	public void deleteAudioFromUserAccount(AudioContent audioContent, String msisdn) {
		User user = userDao.getUserByMSISDN(msisdn);
		user.getAudioContents().remove(audioContent);
		userDao.update(user);
	}
}
