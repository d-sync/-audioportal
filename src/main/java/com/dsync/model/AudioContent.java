package com.dsync.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "audio_content")
public class AudioContent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audio_content_id")
	private long id;

	@Column(name = "artist")
	private String artist;

	@Column(name = "composition")
	private String composition;

	@Column(name = "link")
	private String link;

	@Column(name = "isPopular")
	private boolean isPopular;

	@Column(name = "isNew")
	private boolean isNew;

	@Column(name = "isHit")
	private boolean isHit;

	public AudioContent() {
	}

	public AudioContent(String artist, String composition, String link) {
		this.artist = artist;
		this.composition = composition;
		this.link = link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isPopular() {
		return isPopular;
	}

	public void setPopular(boolean popular) {
		isPopular = popular;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean hit) {
		isHit = hit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AudioContent that = (AudioContent) o;

		if (artist != null ? !artist.equals(that.artist) : that.artist != null) return false;
		if (composition != null ? !composition.equals(that.composition) : that.composition != null) return false;
		return link != null ? link.equals(that.link) : that.link == null;
	}

	@Override
	public int hashCode() {
		int result = artist != null ? artist.hashCode() : 0;
		result = 31 * result + (composition != null ? composition.hashCode() : 0);
		result = 31 * result + (link != null ? link.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return artist + " - " + composition;
	}
}
