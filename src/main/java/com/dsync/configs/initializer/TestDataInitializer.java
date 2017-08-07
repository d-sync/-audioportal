package com.dsync.configs.initializer;

import com.dsync.model.AudioContent;
import com.dsync.model.User;
import com.dsync.services.AudioContentService;
import com.dsync.services.AudioContentServiceImpl;
import com.dsync.services.UserService;
import com.dsync.services.UserServiceImpl;

public class TestDataInitializer {

	static UserService userService = new UserServiceImpl();
	static AudioContentService audioContentService = new AudioContentServiceImpl();

	public static void init() {
		User user = new User("9991394000");
		User user2 = new User("9300000000");

		AudioContent a1 = new AudioContent("Linkin Park", "With You", "link1", true, false, true);
		AudioContent a2 = new AudioContent("New artist", "New song", "link2", false, true, false);
		AudioContent a3 = new AudioContent("Avril Lavigne", "Complicated", "link3", true, false, true);
		AudioContent a4 = new AudioContent("Sam Paganini", "Black Leather", "link4", false, false, true);
		AudioContent a5 = new AudioContent("Sum 41", "With Me", "link5", false, false, true);
		AudioContent a6 = new AudioContent("Luis Fonsi", "Despasito", "link6", true, true, false);
		AudioContent a7 = new AudioContent("Тимати", "Какая-то песня", "link7", true, true, false);

		user.addAudioContent(a1);
		user.addAudioContent(a3);

		user2.addAudioContent(a6);
		user2.addAudioContent(a7);

		audioContentService.add(a1);
		audioContentService.add(a2);
		audioContentService.add(a3);
		audioContentService.add(a4);
		audioContentService.add(a5);
		audioContentService.add(a6);
		audioContentService.add(a6);

		userService.addUser(user);
		userService.addUser(user2);
	}

}
