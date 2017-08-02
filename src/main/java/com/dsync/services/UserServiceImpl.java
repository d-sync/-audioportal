package com.dsync.services;



import com.dsync.dao.UserDao;
import com.dsync.dao.UserDaoImpl;
import com.dsync.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserServiceImpl implements UserService {

	private UserDao userDao = UserDaoImpl.getInstance();

	@Override
	public User getUserById(long id) {
		return userDao.getById(id);
	}

	@Override
	public boolean isUserExists(String name) {
		return userDao.isUserExists(name);
	}

	@Override
	public void addUser(User user) {
		userDao.save(user);
	}
}
