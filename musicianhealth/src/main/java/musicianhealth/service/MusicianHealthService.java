package musicianhealth.service;

import musicianhealth.MusicianHealth;
import musicianhealth.exception.MusicianHealthNotFoundException;

public interface MusicianHealthService {

	public MusicianHealth get(String email) throws MusicianHealthNotFoundException;
	
	public void adjustMusicianHealth(String email,int adjustment);
}
