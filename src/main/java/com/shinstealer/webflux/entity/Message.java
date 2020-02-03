package com.shinstealer.webflux.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

	private String name;
	private String text;

	@JsonCreator
	public Message(@JsonProperty("name") String name, @JsonProperty("text") String text) {
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

}
