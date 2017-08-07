package com.dsync;

import com.dsync.model.AudioContent;

import java.io.Serializable;

public class Message implements Serializable{

	private Operation operation;

	private String data;

	private AudioContent audioContent;

	public Message(Operation operation, AudioContent audioContent) {
		this.operation = operation;
		this.audioContent = audioContent;
	}

	public Message(Operation operation, String data) {
		this.operation = operation;
		this.data = data;
	}

	public Message(Operation operation) {
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

	public String getData() {
		return data;
	}

	public AudioContent getAudioContent() {
		return audioContent;
	}
}
