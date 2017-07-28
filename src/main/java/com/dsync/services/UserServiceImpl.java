package com.dsync.services;



import com.dsync.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserServiceImpl implements UserService {

	private Set<User> users = new HashSet<>();

	{
		users.add(new User("1212"));
		users.add(new User("9999"));
	}

	@Override
	public User getUserById(long id) {
		return null;
	}

	@Override
	public boolean isUserExists(String name) {
		return users.contains(new User(name));
	}
}
