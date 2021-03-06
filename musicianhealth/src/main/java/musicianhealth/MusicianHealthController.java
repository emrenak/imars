package musicianhealth;

import java.util.List;

import musicianhealth.exception.MusicianHealthNotFoundException;
import musicianhealth.service.MusicianHealthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imars.core.domain.MusicianHealth;

@RestController
public class MusicianHealthController {

	@Autowired
	MusicianHealthService musicianHealthService;
	
	@RequestMapping("/musicianhealth/get")
	public MusicianHealth get(@RequestParam(value="email", defaultValue="") String email) throws MusicianHealthNotFoundException{
		return musicianHealthService.get(email);
	}
	
	
	@RequestMapping("/musicianhealth/adjustLevel")
	public void adjustMusicianHealth(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="adjustment") int adjustment){
		musicianHealthService.adjustMusicianHealth(email, adjustment);
	}
	
	@RequestMapping("/musicianhealth/rankByHealthLevel")
	public List<MusicianHealth> rankByHealthLevel(){
		return musicianHealthService.rankByHealthLevel();
	}
}
