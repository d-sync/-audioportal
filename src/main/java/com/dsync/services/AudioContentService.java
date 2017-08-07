package com.dsync.services;

import com.dsync.model.AudioContent;

import java.util.Set;

public interface AudioContentService {

	Set<AudioContent> getUserAudioContent(String msisdn);

	Set<AudioContent> getPopularAudioContent();

	Set<AudioContent> getNewAudioContent();

	Set<AudioContent> getHitsAudioContent();

	void add(AudioContent audioContent);
}
