package musician;

import musician.exception.MusicianNotFoundException;
import musician.service.MusicianService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicianController {

	@Autowired
	MusicianService musicianService;
	
	@RequestMapping("/musician/update")
	public void updateMusician(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="instruments", defaultValue="") String instruments,
			@RequestParam(value="musicStyle", defaultValue="") String musicStyle,
			@RequestParam(value="influences", defaultValue="") String influences) throws MusicianNotFoundException{
		musicianService.updateMusician(email, instruments, musicStyle, influences);
		
	}
	
	@RequestMapping("/musician/get")
	public Musician getMusician(@RequestParam(value="email", defaultValue="") String email) throws MusicianNotFoundException{
		return musicianService.getMusician(email);
		
	}
	
	@RequestMapping("/musician/add")
	public void addMusician(@RequestParam(value="email", defaultValue="") String email, 
			@RequestParam(value="instruments", defaultValue="") String instruments,
			@RequestParam(value="musicStyle", defaultValue="") String musicStyle,
			@RequestParam(value="influences", defaultValue="") String influences) {
		musicianService.addMusician(email, instruments, musicStyle, influences);
		
	}
}
