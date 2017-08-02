package com.dsync.dao;

import com.dsync.model.User;

public interface UserDao extends GenericDao<Long, User> {
	boolean isUserExists(String name);
}
