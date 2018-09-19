package com.imars.core.domain;

import java.util.HashMap;
import java.util.Map;

public class MusicianWealth {

	/*TODO detailed information has to be kept like name of the instrument and value etc*/
	private String email;
	private int level;
	private Map<String, String> assetList = new HashMap<String, String>();
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
	public Map<String, String> getAssetList() {
		return assetList;
	}
	public void setAssetList(Map<String, String> assetList) {
		this.assetList = assetList;
	}



	
	
}
