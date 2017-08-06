package com.dsync.dao;

import com.dsync.model.AudioContent;

import java.util.Set;


public interface AudioContentDao extends GenericDao<Long, AudioContent> {

	Set<AudioContent> getUserAudioContent(String msisdn);

	Set<AudioContent> getPopularAudioContent();

	Set<AudioContent> getNewAudioContent();

	Set<AudioContent> getHitAudioContent();

}
