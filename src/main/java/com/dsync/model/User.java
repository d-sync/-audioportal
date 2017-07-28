package com.dsync.model;

import java.util.HashSet;
import java.util.Set;

public class User {

	private long id;

	private String username;

	private Set<AudioContent> audioContents = new HashSet<>();


	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<AudioContent> getAudioContents() {
		return audioContents;
	}

	public void setAudioContents(Set<AudioContent> audioContents) {
		this.audioContents = audioContents;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return username != null ? username.equals(user.username) : user.username == null;
	}

	@Override
	public int hashCode() {
		return username != null ? username.hashCode() : 0;
	}
}
