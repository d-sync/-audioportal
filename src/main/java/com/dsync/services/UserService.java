package com.dsync.services;


import com.dsync.model.AudioContent;
import com.dsync.model.User;

public interface UserService {


	boolean isUserExists(String name);

	void addUser(User user);

	void buyCurrentTrack(String msisdn, AudioContent audioContent);

	void deleteAudioFromUserAccount(AudioContent audioContent, String msisdn);

}
