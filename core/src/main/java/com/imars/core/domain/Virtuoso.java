package com.imars.core.domain;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

public class Virtuoso {

	private String email;
	private ObjectId musicianId;
	private Map<String, Integer> instrumentList = new HashMap<String, Integer>();
	public ObjectId getMusicianId() {
		return musicianId;
	}
	public void setMusicianId(ObjectId musicianId) {
		this.musicianId = musicianId;
	}
	public Map<String, Integer> getInstrumentList() {
		return instrumentList;
	}
	public void setInstrumentList(Map<String, Integer> virtuosityLevel) {
		this.instrumentList = virtuosityLevel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
