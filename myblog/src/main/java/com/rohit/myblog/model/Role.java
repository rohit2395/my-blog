package com.rohit.myblog.model;

public enum Role {
	
	USER("USER"),ADMIN("ADMIN");
	
	private final String role;
	
	Role(String role) {
		this.role = role;
	}
	
	public String getVal() {
		return this.role;
	}

}
