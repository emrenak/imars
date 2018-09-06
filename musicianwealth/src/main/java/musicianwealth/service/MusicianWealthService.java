package musicianwealth.service;

import java.util.List;

import musicianwealth.MusicianWealth;
import musicianwealth.exception.MusicianWealthNotFoundException;

public interface MusicianWealthService {

	public MusicianWealth get(String email) throws MusicianWealthNotFoundException;
	
	public void adjustMusicianWealth(String email,int adjustment);
	
	public void addAssets(String email,String asset, int numOfAsset);
	
	public List<MusicianWealth> rankByWealthLevel();
}
