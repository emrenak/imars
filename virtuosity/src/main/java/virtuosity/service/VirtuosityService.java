package virtuosity.service;

import java.util.List;

import virtuosity.Virtuoso;
import virtuosity.VirtuosoLevelDTO;
import virtuosity.exception.VirtuosityNotFoundException;

public interface VirtuosityService {

	public Virtuoso get(String email) throws VirtuosityNotFoundException;
	
	public void addInstrument(String email,String instrument);
	
	public void removeInstrument( String email,String instrument);
	
	public void adjustVirtuosityLevel(String email,String instrument,int adjustment);
	
	public List<VirtuosoLevelDTO> rankByVirtuosityLevel(String instrument);
}
