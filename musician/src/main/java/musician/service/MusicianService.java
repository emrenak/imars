package musician.service;

import musician.Musician;
import musician.exception.MusicianNotFoundException;

public interface MusicianService {
	
	public void updateMusician(String email,String instruments,
			String musicStyle, String influences) throws MusicianNotFoundException;
	
	public Musician getMusician(String email) throws MusicianNotFoundException;
	
	public void addMusician(String email,String instruments,
			String musicStyle, String influences);

}
