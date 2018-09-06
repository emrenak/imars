package virtuosity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import virtuosity.exception.VirtuosityNotFoundException;
import virtuosity.service.VirtuosityService;

@RestController
public class VirtuosityController {

	@Autowired
	VirtuosityService virtuosityService;
	
	@RequestMapping("/virtuosity/get")
	public Virtuoso get(@RequestParam(value="email", defaultValue="") String email) throws VirtuosityNotFoundException{
		return virtuosityService.get(email);
	}
	
	@RequestMapping("/virtuosity/addInstrument")
	public void addInstrument(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="instrument", defaultValue="") String instrument){
		virtuosityService.addInstrument(email, instrument);
	}
	
	@RequestMapping("/virtuosity/removeInstrument")
	public void removeInstrument(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="instrument", defaultValue="") String instrument){
		virtuosityService.removeInstrument(email, instrument);
		
	}
	
	@RequestMapping("/virtuosity/adjustLevel")
	public void adjustVirtuosityLevel(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="instrument", defaultValue="") String instrument,
			@RequestParam(value="adjustment") int adjustment){
		virtuosityService.adjustVirtuosityLevel(email, instrument, adjustment);
	}
	
	@RequestMapping("/virtuosity/rankByVirtuosityLevel")
	public List<VirtuosoLevelDTO> rankByVirtuosityLevel(String instrument){
		return virtuosityService.rankByVirtuosityLevel(instrument);
	}
	
}
