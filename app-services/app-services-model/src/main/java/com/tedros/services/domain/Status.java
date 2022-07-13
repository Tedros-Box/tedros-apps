package com.tedros.services.domain;

public enum Status {

	ENABLED("#{label.enabled}"), 
	DISABLED("#{label.disabled}"), 
	SUSPENDED("#{label.suspended}");
	
	private String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
