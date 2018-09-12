package musician.service;

import java.util.List;

import musician.Musician;
import musician.exception.MusicianNotFoundException;

public interface MusicianService {
	
	public void updateMusician(String email,String instruments,
			String musicStyle, String influences) throws MusicianNotFoundException;
	
	public Musician getMusician(String email) throws MusicianNotFoundException;
	
	public void addMusician(String email,String instruments,
			String musicStyle, String influences);
	
	public List<Musician> getAllMusicians() ;

}
