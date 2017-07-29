package com.dsync;

import com.dsync.model.AudioContent;

import java.io.Serializable;
import java.util.Set;

public class Message implements Serializable{

	private Operation operation;

	private String data;

	private Set<AudioContent> audioContents;

	public Message(Operation operation, String data) {
		this.operation = operation;
		this.data = data;
	}

	public Message(Operation operation) {
		this.operation = operation;
	}

	public Message(Operation operation, Set<AudioContent> audioContents) {
		this.operation = operation;
		this.audioContents = audioContents;
	}

	public Operation getOperation() {
		return operation;
	}

	public String getData() {
		return data;
	}
}
