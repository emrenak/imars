package musicianhealth.service;

import java.util.List;

import com.imars.core.domain.MusicianHealth;

import musicianhealth.exception.MusicianHealthNotFoundException;

public interface MusicianHealthService {

	public MusicianHealth get(String email) throws MusicianHealthNotFoundException;
	
	public void adjustMusicianHealth(String email,int adjustment);
	
	public List<MusicianHealth> rankByHealthLevel();
}
