package com.dsync.services;


import com.dsync.model.User;

public interface UserService {

	User getUserById(long id);

	boolean isUserExists(String name);
}
