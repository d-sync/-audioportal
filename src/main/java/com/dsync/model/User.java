package com.dsync.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_msisdn")
	private String msisdn;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_to_audio",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "audio_content_id")
	)
	private Set<AudioContent> audioContents = new HashSet<>();


	public User() {
	}

	public User(String msisdn) {
		this.msisdn = msisdn;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {

	}
	public void addAudioContent(AudioContent audioContent) {
		audioContents.add(audioContent);
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

		return msisdn != null ? msisdn.equals(user.msisdn) : user.msisdn == null;
	}

	@Override
	public int hashCode() {
		return msisdn != null ? msisdn.hashCode() : 0;
	}
}
