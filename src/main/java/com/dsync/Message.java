package com.dsync;



import java.io.Serializable;
import java.util.Set;

public class Message implements Serializable{

	private Operation operation;

	private String data;

	private Set<String> items;


	public Message(Operation operation, String data) {
		this.operation = operation;
		this.data = data;
	}

	public Message(Operation operation) {
		this.operation = operation;
	}

	public Message(String data, Set<String> items) {
		this.data = data;
		this.items = items;
	}

	public Message(String data) {
		this.data = data;
	}

	public Operation getOperation() {
		return operation;
	}

	public String getData() {
		return data;
	}
}
