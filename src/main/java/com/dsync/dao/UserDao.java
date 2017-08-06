package com.dsync.dao;

import com.dsync.model.AudioContent;
import com.dsync.model.User;

public interface UserDao extends GenericDao<Long, User> {
	boolean isUserExists(String name);

	void updateUser(String msisdn, AudioContent audioContent);

	User getUserByMSISDN(String msisdn);

	void update(User user);
}
