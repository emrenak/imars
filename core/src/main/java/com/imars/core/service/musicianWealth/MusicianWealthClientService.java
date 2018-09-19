package com.imars.core.service.musicianWealth;

import com.imars.core.domain.MusicianWealth;

public interface MusicianWealthClientService {
	public MusicianWealth getWealth(String email, String serviceUrl) throws Exception;
	public void addAssets(String email, String asset, int quantity, String serviceUrl) throws Exception;
}
