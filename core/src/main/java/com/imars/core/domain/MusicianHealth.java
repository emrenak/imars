package com.imars.core.domain;

public class MusicianHealth {

	private String email;
	private int level;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "MusicianHealth [email=" + email + ", level=" + level + "]";
	}
	
	
}
