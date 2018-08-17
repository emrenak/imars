package virtuosity.service;

import virtuosity.Virtuoso;
import virtuosity.exception.VirtuosityNotFoundException;

public interface VirtuosityService {

	public Virtuoso get(String email) throws VirtuosityNotFoundException;
	
	public void addInstrument(String email,String instrument);
	
	public void removeInstrument( String email,String instrument);
	
	public void adjustVirtuosityLevel(String email,String instrument,int adjustment);
}
